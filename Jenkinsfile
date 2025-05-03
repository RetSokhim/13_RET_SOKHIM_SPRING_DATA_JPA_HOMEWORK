pipeline {
    agent any

    tools {
        gradle 'Gradle'    
        jdk 'JDK 21'
    }

    environment {
        SONAR_HOST_URL = "http://sonarqube:9000"
        SONAR_PROJECT_KEY = "r-demo"
        SONAR_PROJECT_NAME = "r-demo"
        JAVA_HOME = '/usr/lib/jvm/jdk-21'
    }

    stages {
        stage('Git Checkout') {
            steps {
                deleteDir()
                git url: 'https://github.com/PhannSothyrith/12_PHANN_SOTHYRITH_JPA_HIBERNATE_2_HOMEWORK.git', branch: 'master'
                echo '✅ Git Checkout Completed'
            }
        }

        stage('Docker Login') {
            steps {                                            
                withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withEnv(["JAVA_HOME=${tool 'JDK 21'}", "PATH+JAVA=${tool 'JDK 21'}/bin"]) {
                    withCredentials([string(credentialsId: 'usonar-token', variable: 'SONAR_TOKEN')]) {
                        sh 'chmod +x gradlew'
                        sh '''
                            ./gradlew build
                            ./gradlew sonar \
                                -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                                -Dsonar.projectName="$SONAR_PROJECT_NAME" \
                                -Dsonar.host.url=$SONAR_HOST_URL \
                                -Dsonar.token=$SONAR_TOKEN \
                                -Dsonar.java.binaries=build/classes/java/main \
                                -Dsonar.coverage.jacoco.xmlReportPaths=build/reports/jacoco/test/jacocoTestReport.xml
                        '''
                    }
                }
            }
        }

        stage('Custom Quality Rule - Fail on Bad Metrics') {
            steps {
                script {  
                                            // This credentials is the user token in SonarQube.
                    withCredentials([string(credentialsId: 'usonar-token', variable: 'SONAR_TOKEN')]) {
                        def response = sh(
                            script: """
                                curl -s -u $SONAR_TOKEN: "$SONAR_HOST_URL/api/measures/component?component=$SONAR_PROJECT_KEY&metricKeys=code_smells,vulnerabilities,duplicated_lines_density,security_rating,reliability_rating"
                            """,
                            returnStdout: true
                        ).trim()
                        def json = readJSON text: response
                        def measures = json.component.measures.collectEntries { [(it.metric): it.value] }

                        def metricsToCheck = [
                            'code_smells', 
                            'vulnerabilities', 
                            'duplicated_lines_density', 
                            'security_rating', 
                            'reliability_rating'
                        ]
                        def failedMetrics = metricsToCheck.findAll { key ->
                            def value = measures[key]
                            try {
                                return value.toFloat() > 50
                            } catch (Exception e) {
                                return false
                            }
                        }

                        if (failedMetrics) {
                            error "❌ Custom Quality Rule failed. Metrics above threshold (50): ${failedMetrics.join(', ')}"
                        } else {
                            echo "✅ Custom Quality Rule passed."
                        }
                    }
                }
            }
        }

        stage('SonarQube Report to Slack') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'usonar-token', variable: 'SONAR_TOKEN')]) {
                        def response = sh(
                            script: '''
                                curl -s -u $SONAR_TOKEN: "$SONAR_HOST_URL/api/measures/component?component=$SONAR_PROJECT_KEY&metricKeys=code_smells,bugs,vulnerabilities,coverage,new_coverage,duplicated_lines_density,sqale_rating,reliability_rating,security_rating"
                            ''',
                            returnStdout: true
                        ).trim()

                        def json = readJSON text: response
                        def measures = json.component.measures.collectEntries { [(it.metric): it.value] }

                        def projectUrl = "http://localhost:9002/dashboard?id=${env.SONAR_PROJECT_KEY}"

                        env.SONAR_MSG = """
*SonarQube Full Report for ${env.SONAR_PROJECT_NAME}:*
• 🔍 Code Smells: *${measures['code_smells'] ?: '0'}*
• 🐞 Bugs: *${measures['bugs'] ?: '0'}*
• 🔓 Vulnerabilities: *${measures['vulnerabilities'] ?: '0'}*
• 🧪 Coverage: *${measures['coverage'] ?: 'N/A'}%*
• ♻️ Duplicated Lines: *${measures['duplicated_lines_density'] ?: '0'}%*
• 📉 Maintainability Rating: *${measures['sqale_rating'] ?: 'N/A'}*
• ✅ Reliability Rating: *${measures['reliability_rating'] ?: 'N/A'}*
• 🔐 Security Rating: *${measures['security_rating'] ?: 'N/A'}*
• 🔗 [View Project on SonarQube](${projectUrl})
"""
                    }
                }
            }
        }

        stage("Build Docker Image") {
            steps {
                sh 'docker build -t soklay14/spring-datajpa .'
            }
        }

        stage("Push to Docker Hub") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                    sh '''
                        docker tag soklay14/spring-datajpa $DOCKERHUB_USERNAME/spring-datajpa:latest
                        echo "$DOCKERHUB_PASSWORD" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin
                        docker push $DOCKERHUB_USERNAME/spring-datajpa:latest
                    '''
                }
            }
        }
    }

    post {
        success {
            slackSend channel: '#sonarqube', color: 'good', message: "${env.SONAR_MSG}"
        }
        failure {
        slackSend channel: '#sonarqube', color: 'danger', message: """
            ❌ *SonarQube analysis failed for project* _${env.SONAR_PROJECT_NAME}_ 
            • 🚨 *Reason*: Custom Quality Rule Failed or Issues Found Above Threshold 
            • 🔗 [View SonarQube Report](http://localhost:9002/dashboard?id=${env.SONAR_PROJECT_KEY})
        """
    }
    }
}
