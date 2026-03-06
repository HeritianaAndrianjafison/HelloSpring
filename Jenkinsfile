pipeline {
    agent any

    environment {
        WORKSPACE_DIR = "/home/heritiana/HelloSpring"   // Répertoire stable pour le projet et logs
        APP_PORT = "1234"                               // Port du serveur Spring Boot
    }

    stages {

        stage('Prepare Workspace') {
            steps {
                sh """
                # Crée le répertoire si inexistant
                mkdir -p ${WORKSPACE_DIR}
                cd ${WORKSPACE_DIR}
                """
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/HeritianaAndrianjafison/HelloSpring.git'
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