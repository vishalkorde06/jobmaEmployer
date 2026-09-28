pipeline {
    agent any

    triggers {
        cron('0 9 * * *')
    }

    environment {
        NOTIFICATION_EMAIL = 'vishalkorde42@gmail.com'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Test Execution') {
            steps {
                bat 'mvn clean test -Dbrowser=chrome'
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'ExtentReports',
                    reportFiles: '*.html',
                    reportName: 'Extent Test Execution Report'
                ])
            }
        }
    }

    post {
        always {
            script {
                def buildStatus = currentBuild.result ?: 'SUCCESS'
                
                // Fallback approach: find latest file using standard pattern matching
                // If your file has a static name or wildcard, specify it clearly here:
                def reportPattern = "ExtentReports/*.html"

                emailext (
                    to: "${NOTIFICATION_EMAIL}",
                    subject: "Automation Report - Job: ${JOB_NAME} [Build #${BUILD_NUMBER}] - Status: ${buildStatus}",
                    body: """
                    <h3>Execution Suite Results (Chrome)</h3>
                    <p><b>Repository:</b> jobmaEmployer</p>
                    <p><b>Build Number:</b> #${BUILD_NUMBER}</p>
                    <p><b>Execution Status:</b> ${buildStatus}</p>
                    <p><b>Jenkins Dashboard Link:</b> <a href="${BUILD_URL}">${BUILD_URL}</a></p>
                    """,
                    mimeType: 'text/html',
                    attachLog: true,
                    attachmentsPattern: reportPattern 
                )
            }
        }
    }
}
