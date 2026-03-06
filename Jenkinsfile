pipeline {
    agent any

    environment {
        WORKSPACE_DIR = "/home/heritiana/HelloSpring"   // Répertoire du projet
        APP_PORT = "1234"                               // Port du serveur Spring Boot
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64" // Forcer Java 17
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {

        stage('Prepare Workspace') {
            steps {
                sh """
                mkdir -p ${WORKSPACE_DIR}
                """
            }
        }

        stage('Checkout') {
            steps {
                sh """
                # Supprime tout contenu précédent
                rm -rf ${WORKSPACE_DIR}/*

                # Clone le dépôt dans WORKSPACE_DIR
                git clone -b main https://github.com/HeritianaAndrianjafison/HelloSpring.git ${WORKSPACE_DIR}
                """
            }
        }

        stage('Build') {
            steps {
                sh """
                cd ${WORKSPACE_DIR}
                mvn clean package
                """
            }
        }

        stage('Run Jar') {
            steps {
                sh """
                cd ${WORKSPACE_DIR}
                JAR_FILE=\$(ls target/*.jar | head -n 1)

                if [ -z "\$JAR_FILE" ]; then
                    echo "Erreur : aucun fichier JAR trouvé dans target/"
                    exit 1
                fi

                echo "Starting \$JAR_FILE on port ${APP_PORT}..."

                # Stop any existing instance of this JAR
                pkill -f \$JAR_FILE || true

                # Lancer le jar en arrière-plan et rediriger les logs
                nohup java -jar \$JAR_FILE --server.address=0.0.0.0 --server.port=${APP_PORT} > ${WORKSPACE_DIR}/app.log 2>&1 &
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