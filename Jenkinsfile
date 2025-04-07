pipeline {
    agent any
    tools {
        gradle 'Gradle' // Assuming you have a Gradle tool configured in Jenkins
        jdk 'JDK 21'    // Ensure this matches the name you configured for JDK 21 in Jenkins
    }

    environment {
        SONAR_HOST_URL = "http://localhost:9000" // Use the name of the container or hostname here
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/RetSokhim/13_RET_SOKHIM_SPRING_DATA_JPA_HOMEWORK.git']])
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') { // Ensure 'sonarqube' is correctly set up in Jenkins
                    withCredentials([string(credentialsId: 'gradle-token', variable: 'GRADLE_TOKEN')]) { // Corrected credentialsId
                        sh '''
                            gradle clean build sonarqube \
                                -Dsonar.projectKey=gradle \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${GRADLE_TOKEN}
                        '''
                        echo 'SonarQube Analysis Completed'
                    }
                }
            }
        }
    }
}
