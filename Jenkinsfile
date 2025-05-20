pipeline {
    agent any

    tools {
        maven 'Maven 3.8.7' // Usa el nombre del Maven que configuraste en Jenkins
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

        stage('Generar artefacto .jar') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        success {
            echo '¡Construcción exitosa! Artefacto generado.'
        }
        failure {
            echo 'La construcción falló.'
        }
    }
}
