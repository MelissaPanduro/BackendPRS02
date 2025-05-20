pipeline {
    agent any

    tools {
        maven 'Maven 3.8.7' // Este nombre debe coincidir con el configurado en Jenkins (ver nota abajo)
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
