node {
    def mvn = tool (name: 'maven3', type: 'maven') + '/bin/mvn'

    stage('SCM Checkout'){
        git branch: 'main',
        credentialsId: 'pmerdala',
        url: 'https://github.com/PMerdala/testing-java-junit5.git'
    }

    stage('Mvn Package'){
        env.JAVA_HOME = tool (name: 'openjdk-11', type: 'jdk')
        env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        sh "${mvn} clean test package"
    }

    stage('Email Notification'){
        mail bcc:'', body:"""Hi Team, You build successfully deployed
                             Job URL: ${env.JOB_URL}
                                        Job Name: ${env.JOB_NAME}
    Thanks,
    DevOps Team""", cc:'',from:'P.Merdala@interia.pl', replyTo:'', subject:"${env.JOB_NAME} Success", to:'P.Merdala@interia.pl'
    }
}