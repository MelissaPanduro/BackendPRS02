pipeline {
    agent {
        docker { image 'maven:3.8.7-openjdk-17' }
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
