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
                
                // 1. Locate all HTML reports safely in the Workspace
                def reports = findFiles(glob: 'ExtentReports/*.html')
                
                def latestReportPath = ''
                if (reports.length > 0) {
                    // Sort by last modified timestamp to get ONLY the latest file
                    def latestFile = reports.max { it.lastModified }
                    latestReportPath = "ExtentReports/${latestFile.name}"
                    echo "Attaching report: ${latestReportPath}"
                } else {
                    echo "WARNING: No HTML reports found inside ExtentReports directory!"
                }

                // 2. Send email with the attached report
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
