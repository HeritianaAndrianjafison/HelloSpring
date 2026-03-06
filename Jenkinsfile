pipeline {
    agent any

    environment {
        WORKSPACE_DIR = "/home/heritiana/HelloSpring"
        IMAGE_NAME = "hellospring"
        CONTAINER_NAME = "hellospring-container"
        APP_PORT = "1234"
        DOCKER_BASE_IMAGE = "openjdk:17-slim"
        REMOTE_HOST = "172.29.191.35"
        REMOTE_USER = "root"
        SSH_KEY = "/home/heritiana/.ssh/id_rsa_jenkins" // clé SSH correcte
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

                if [ ! -f Dockerfile ]; then
                    cat > Dockerfile <<EOF
FROM ${DOCKER_BASE_IMAGE}
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE ${APP_PORT}
ENTRYPOINT ["java","-jar","app.jar"]
EOF
                fi

                docker rmi -f ${IMAGE_NAME}:latest || true
                docker build -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Deploy Container on Jenkins') {
            steps {
                sh '''
                echo "Déploiement du conteneur sur Jenkins..."
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true
                docker run -d --name ${CONTAINER_NAME} -p ${APP_PORT}:${APP_PORT} ${IMAGE_NAME}:latest
                '''
            }
        }

        stage('Deploy to Ubuntu-A via SSH') {
            steps {
                sh '''
                echo "Déploiement sur Ubuntu-A via SSH..."

                # Vérifier que bzip2 est installé sur Jenkins
                if ! command -v bzip2 &> /dev/null; then
                    sudo apt update && sudo apt install -y bzip2
                fi

                # Copier et charger l'image Docker sur le serveur distant
                docker save ${IMAGE_NAME}:latest | bzip2 | ssh -i ${SSH_KEY} -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    # Installer bzip2 si absent
                    if ! command -v bzip2 &> /dev/null; then
                        apt update && apt install -y bzip2
                    fi

                    # Installer Docker si absent
                    if ! command -v docker &> /dev/null; then
                        apt update && apt install -y docker.io
                    fi

                    # Installer Java si absent
                    if ! java -version &> /dev/null; then
                        apt update && apt install -y openjdk-17-jdk
                    fi

                    # Déployer le conteneur
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                    docker run -d --name ${CONTAINER_NAME} -p ${APP_PORT}:${APP_PORT} ${IMAGE_NAME}:latest
                '
                '''
            }
        }
    }

    post {
        success {
            echo "Application déployée dans Docker sur Jenkins et Ubuntu-A (port ${APP_PORT})"
        }
        failure {
            echo "Le pipeline a échoué"
        }
    }
}