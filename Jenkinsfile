pipeline {
	agent any
    tools {
		gradle 'Gradle' // Assuming you have a Gradle tool configured in Jenkins
    }

    environment {
		SONAR_HOST_URL = "http://localhost:9000"
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
				withSonarQubeEnv('sonarqube') {
					withCredentials([string(credentialsId: 'gradle-token', variable: 'GRADLE-TOKEN')]) { // Corrected credentialsId
                        sh '''
                            gradle clean build sonarqube \
                                -Dsonar.projectKey=Spring_data_JPA_homework \
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