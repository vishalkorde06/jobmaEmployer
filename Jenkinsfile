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

        stage('Run Automation Tests') {
            steps {
                bat 'mvn clean test -Dbrowser=chrome'
            }
        }

        stage('Publish Execution Reports') {
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
                
                // Locate the latest generated HTML report using standard Java file handling
                def latestReport = ''
                def reportDir = new File("${WORKSPACE}/ExtentReports")
                
                if (reportDir.exists()) {
                    def files = reportDir.listFiles({ dir, name -> name.endsWith('.html') } as FileFilter)
                    if (files && files.length > 0) {
                        def latestFile = files.max { it.lastModified() }
                        latestReport = "ExtentReports/${latestFile.name}"
                    }
                }

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
                    attachmentsPattern: latestReport // Attaches ONLY the single latest report safely
                )
            }
        }
    }
}
