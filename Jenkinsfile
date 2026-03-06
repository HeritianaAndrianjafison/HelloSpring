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
        SSH_KEY = "/root/.ssh/id_rsa_jenkins"
        SSH_PASSPHRASE = "1234"
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

                bash -c "
                # Démarrage de ssh-agent
                eval \$(ssh-agent -s)

                # Ajout de la clé avec passphrase
                echo '${SSH_PASSPHRASE}' | ssh-add ${SSH_KEY}

                # Copier l'image Docker sur le serveur distant
                docker save ${IMAGE_NAME}:latest | bzip2 | ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'bunzip2 | docker load'

                # Exécution des commandes sur le serveur distant
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} bash -c \\
                'if ! command -v docker &> /dev/null; then
                    apt update && apt install -y docker.io
                fi

                if ! java -version &> /dev/null; then
                    apt update && apt install -y openjdk-17-jdk
                fi

                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true
                docker run -d --name ${CONTAINER_NAME} -p ${APP_PORT}:${APP_PORT} ${IMAGE_NAME}:latest'

                # Arrêter ssh-agent
                ssh-agent -k
                "
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