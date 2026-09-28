pipeline {
    agent any

  // Triggers execution every day at 9 AM
    triggers {
        cron('0 9 * * *')
    }

    environment {
        // Updated to your email address
        NOTIFICATION_EMAIL = 'vishalkorde42@gmail.com'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Run Automation Tests') {
            steps {
                // If Jenkins agent runs on Windows, change 'sh' to 'bat'
                bat 'mvn clean test' 
            }
        }

        stage('Publish Execution Reports') {
            steps {
                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'Test Execution Report'
                ])
            }
        }
    }

    post {
        always {
            emailext (
                to: "${NOTIFICATION_EMAIL}",
                subject: "Automation Execution Report - Job: ${JOB_NAME} [Build #${BUILD_NUMBER}] - Status: ${currentBuild.result ?: 'SUCCESS'}",
                body: """
                <h3>Automation Suite Results</h3>
                <p><b>Repository:</b> jobmaEmployer</p>
                <p><b>Build Number:</b> #${BUILD_NUMBER}</p>
                <p><b>Status:</b> ${currentBuild.result ?: 'SUCCESS'}</p>
                <p><b>Jenkins Build Link:</b> <a href="${BUILD_URL}">${BUILD_URL}</a></p>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}
