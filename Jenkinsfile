pipeline {
    agent any

    tools {
        gradle 'Gradle'      // Make sure this matches the Gradle tool name in Jenkins
        jdk 'JDK 21'         // Make sure this matches your installed JDK
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scm
                echo '✅ Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Connect to configured SonarQube server (name must match Jenkins config)
                withSonarQubeEnv('sonarqube') {
                    script {
                        // Run the Gradle sonar task with SonarQube details
                        sh """
                            ./gradlew clean build sonar \
                              -Dsonar.projectKey=gradle \
                              -Dsonar.projectName="Spring_data_JPA_homework" \
                              -Dsonar.host.url=http://localhost:9000 \
                              -Dsonar.login=sqp_cdac00549725385bd13521fc85a2184c6de6c6a1

                        """
                    }
                }
            }
        }
    }
}
