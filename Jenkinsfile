pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('SONAR_TOKEN') // ID de la credencial secreta de texto que guardaste en Jenkins
    }

    tools {
        git 'DefaultGit'               // Herramienta Git configurada en Jenkins
        maven 'Maven 3.8.7'            // Debe coincidir con el nombre configurado en Jenkins
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
                withSonarQubeEnv('SonarQube') { // Asegúrate que "SonarQube" sea el nombre configurado en Jenkins
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=BackendPRS02 \
                        -Dsonar.sources=src \
                        -Dsonar.java.binaries=target \
                        -Dsonar.login=${SONAR_TOKEN}
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
