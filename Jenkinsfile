pipeline {
    agent any

    environment {
        WORKSPACE_DIR = "/home/heritiana/HelloSpring"
        IMAGE_NAME = "hellospring"
        CONTAINER_NAME = "hellospring-container"
        APP_PORT = "1234"
        DOCKER_BASE_IMAGE = "openjdk:17-slim"
    }

    stages {

        stage('Prepare Workspace') {
            steps {
                sh '''
                echo "Préparation du workspace..."
                rm -rf ${WORKSPACE_DIR} || true
                mkdir -p ${WORKSPACE_DIR}
                '''
            }
        }

        stage('Checkout') {
            steps {
                sh '''
                echo "Clonage du dépôt Git..."
                git clone -b main https://github.com/HeritianaAndrianjafison/HelloSpring.git ${WORKSPACE_DIR}
                '''
            }
        }

        stage('Build JAR') {
            steps {
                sh '''
                echo "Construction du JAR avec Maven..."
                cd ${WORKSPACE_DIR}
                mvn clean package
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                echo "Construction de l'image Docker..."
                cd ${WORKSPACE_DIR}

                # Crée un Dockerfile minimal si inexistant
                if [ ! -f Dockerfile ]; then
                    cat > Dockerfile <<EOF
FROM ${DOCKER_BASE_IMAGE}
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE ${APP_PORT}
ENTRYPOINT ["java","-jar","app.jar"]
EOF
                fi

                # Nettoyage d'anciennes images pour éviter conflit
                docker rmi -f ${IMAGE_NAME}:latest || true

                docker build -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                echo "Déploiement du conteneur..."
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