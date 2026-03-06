pipeline {
    agent any

    environment {
        WORKSPACE_DIR = "/home/heritiana/HelloSpring"
        IMAGE_NAME = "hellospring"
        CONTAINER_NAME = "hellospring-container"
        APP_PORT = "1234"
    }

    stages {

        stage('Prepare Workspace') {
            steps {
                sh '''
                rm -rf ${WORKSPACE_DIR} || true
                mkdir -p ${WORKSPACE_DIR}
                '''
            }
        }

        stage('Checkout') {
            steps {
                sh '''
                git clone -b main https://github.com/HeritianaAndrianjafison/HelloSpring.git ${WORKSPACE_DIR}
                '''
            }
        }

        stage('Build JAR') {
            steps {
                sh '''
                cd ${WORKSPACE_DIR}
                mvn clean package
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                cd ${WORKSPACE_DIR}
                docker build -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true

                docker run -d \
                --name ${CONTAINER_NAME} \
                -p ${APP_PORT}:${APP_PORT} \
                ${IMAGE_NAME}:latest
                '''
            }
        }
    }

    post {
        success {
            echo "Application déployée dans Docker sur le port ${APP_PORT}"
        }
        failure {
            echo "Le pipeline a échoué"
        }
    }
}