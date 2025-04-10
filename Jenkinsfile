pipeline {
    agent any
    environment {
        SONARQUBE = 'sonarqube' // Name of your SonarQube server in Jenkins config
        SONAR_TOKEN = credentials('gradle2') // Fetch the token from Jenkins credentials
    }
    stages {
        stage('Build') {
            steps {
                script {
                    // Run build command and handle failure
                    try {
                        def buildResult = sh(script: './gradlew build', returnStatus: true)
                        if (buildResult != 0) {
                            error "Build failed with status code ${buildResult}, stopping pipeline."
                        }
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
                        // Run SonarQube analysis and handle failure
                        try {
                            def sonarResult = sh(script: './gradlew sonarqube -Dsonar.projectKey=gradle2 -Dsonar.token=$SONAR_TOKEN', returnStatus: true)
                            if (sonarResult != 0) {
                                error "SonarQube analysis failed with status code ${sonarResult}, stopping pipeline."
                            }
                        } catch (Exception e) {
                            currentBuild.result = 'FAILURE'
                            throw e
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Build and SonarQube analysis completed successfully.'
        }
        failure {
            echo 'There was an issue with the build or SonarQube analysis.'
        }
    }
}
