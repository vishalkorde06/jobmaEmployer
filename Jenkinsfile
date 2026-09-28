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
                
                // 1. Safely find the latest HTML file path
                def latestReportPath = ''
                try {
                    def folder = new File("${WORKSPACE}/ExtentReports")
                    if (folder.exists()) {
                        def htmlFiles = folder.listFiles().findAll { it.name.endsWith('.html') }
                        if (htmlFiles) {
                            def newestFile = htmlFiles.max { it.lastModified() }
                            latestReportPath = "ExtentReports/${newestFile.name}"
                        }
                    }
                } catch (Exception e) {
                    echo "File detection note: ${e.message}"
                }

                // 2. Archive the specific latest report so Jenkins recognizes it as an attachment source
                if (latestReportPath != '') {
                    archiveArtifacts artifacts: latestReportPath, onlyIfSuccessful: false
                }

                // 3. Send the email with the attached report
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
