pipeline {
    agent any

    environment {
        WORKSPACE_DIR = "/home/heritiana/HelloSpring"
        APP_PORT = "1234"
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {

        stage('Prepare Workspace') {
            steps {
                sh """
                # Supprime complètement le répertoire si existant
                if [ -d "${WORKSPACE_DIR}" ]; then
                    rm -rf "${WORKSPACE_DIR}"
                fi
                mkdir -p "${WORKSPACE_DIR}"
                """
            }
        }

        stage('Checkout') {
            steps {
                sh """
                git clone -b main https://github.com/HeritianaAndrianjafison/HelloSpring.git "${WORKSPACE_DIR}"
                """
            }
        }

        stage('Build') {
            steps {
                sh """
                cd "${WORKSPACE_DIR}"
                mvn clean package
                """
            }
        }

        stage('Run Jar') {
            steps {
                sh """
                cd "${WORKSPACE_DIR}"
                JAR_FILE=\$(ls target/*.jar | head -n 1)

                if [ -z "\$JAR_FILE" ]; then
                    echo "Erreur : aucun fichier JAR trouvé dans target/"
                    exit 1
                fi

                echo "Starting \$JAR_FILE on port ${APP_PORT}..."

                pkill -f \$JAR_FILE || true

                nohup java -jar \$JAR_FILE --server.address=0.0.0.0 --server.port=${APP_PORT} > "${WORKSPACE_DIR}/app.log" 2>&1 &
                """
            }
        }
    }

    post {
        success {
            echo "Build et déploiement terminés ! Le serveur Spring Boot écoute sur le port ${APP_PORT}"
        }
        failure {
            echo "Le pipeline a échoué."
        }
    }
}