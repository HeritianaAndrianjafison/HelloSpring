pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/HeritianaAndrianjafison/HelloSpring.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Run Jar') {
            steps {
                sh '''
                JAR_FILE=$(ls target/*.jar | head -n 1)
                echo "Starting $JAR_FILE..."
                # stop any existing instance
                pkill -f $JAR_FILE || true
                # run the new jar in background
                nohup java -jar $JAR_FILE > app.log 2>&1 &
                '''
            }
        }
    }
}