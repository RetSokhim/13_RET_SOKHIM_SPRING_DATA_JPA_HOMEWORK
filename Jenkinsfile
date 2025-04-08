pipeline {
	agent any
    tools {
		gradle 'Gradle'
		jdk 'jdk21'
    }
    environment {
		SONAR_HOST_URL = "http://localhost:9000"
        SONAR_LOGIN = "sqp_cdac00549725385bd13521fc85a2184c6de6c6a1"  // Ensure your SonarQube token is correct
    }
    stages {
		stage('Git Checkout') {
			steps {
				checkout scm
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
			steps {
				withSonarQubeEnv('sonarqube') {  // Make sure SonarQube is configured in Jenkins
                    withCredentials([string(credentialsId: 'gradle-token', variable: 'GRADLE_TOKEN')]) {
					sh """
                            ./gradlew clean build \
                                -Dsonar.projectKey=gradle \
                                -Dsonar.projectName="Spring_data_JPA_homework" \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${SONAR_LOGIN}
                        """
                        echo 'SonarQube Analysis Completed'
                    }
                }
            }
        }
    }
}
