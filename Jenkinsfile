pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('SONAR_TOKEN') // Asegúrate de que esta credencial exista
    }

    tools {
        maven 'Maven 3.8.7'
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                git branch: 'develop', url: 'https://github.com/MelissaPanduro/BackendPRS02.git'
            }
        }

        stage('Obtener rama actual') {
            steps {
                script {
                    env.BRANCH_NAME = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
                    echo "🟢 Rama actual: ${env.BRANCH_NAME}"
                }
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
                echo "🔍 Ejecutando análisis SonarQube para la rama \"${env.BRANCH_NAME}\""
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=BackendPRS02-${env.BRANCH_NAME} \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java \
                        -Dsonar.java.binaries=target \
                        -Dsonar.token=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Generar artefacto .jar') {
            steps {
                script {
                    sh 'mvn package'
                    def jarName = sh(script: "ls target/*.jar | grep -v 'original-' | head -n 1", returnStdout: true).trim()
                    def newJarName = jarName.replace(".jar", "-${env.BRANCH_NAME}.jar")
                    sh "mv ${jarName} ${newJarName}"
                    echo "📦 Artefacto renombrado: ${newJarName}"
                    // Guardar el nombre para usarlo en archiveArtifacts
                    env.RENAMED_JAR = newJarName
                }
                archiveArtifacts artifacts: "${env.RENAMED_JAR}", fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ ¡Construcción exitosa! Artefacto generado, archivado y análisis completado.'
        }
        failure {
            echo '❌ La construcción falló o el análisis falló.'
        }
    }
}
