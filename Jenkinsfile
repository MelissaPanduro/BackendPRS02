pipeline {
    agent any

    stages {
        stage('Clonar repositorio') {
            steps {
                // Indica la rama develop explícitamente
                git branch: 'develop', url: 'https://github.com/MelissaPanduro/BackendPRS02.git'
            }
        }

        stage('Compilar con Maven') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Generar .jar') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
