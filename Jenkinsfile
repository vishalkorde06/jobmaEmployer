import java.text.SimpleDateFormat

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
                
                // Format the exact timestamp matching your Java getCurrentTime() method
                def dateFormat = new SimpleDateFormat("EEEE_yyyy_MM_dd_HH_mm")
                def formattedTimestamp = dateFormat.format(new Date())
                
                // Construct the exact filename pattern matching your Java setExtent() method
                def latestReportPath = "ExtentReports/Report_${formattedTimestamp}.html"

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
                    attachmentsPattern: latestReportPath
                )
            }
        }
    }
}
