pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('SONAR_TOKEN') // Asegúrate de que esta credencial exista
    }

    tools {
        maven 'Maven 3.8.7' // Nombre correcto de tu instalación de Maven
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                git branch: 'develop', url: 'https://github.com/MelissaPanduro/BackendPRS02.git'
            }
        }

        stage('Compilar con Maven') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Ejecutar pruebas unitarias') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Análisis SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') { // Este nombre debe coincidir con el configurado en "Configure System"
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=BackendPRS02 \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java \
                        -Dsonar.java.binaries=target \
                        -Dsonar.token=${SONAR_TOKEN} \
                        -Dsonar.branch.name=develop
                    '''
                }
            }
        }

        stage('Generar artefacto .jar') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        success {
            echo '¡Construcción exitosa! Artefacto generado y análisis completado.'
        }
        failure {
            echo 'La construcción falló o el análisis falló.'
        }
    }
}
