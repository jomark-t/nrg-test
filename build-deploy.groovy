// Referenced the steps from https://www.jenkins.io/doc/book/pipeline/jenkinsfile/

pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'jt/e3:1.0'
        KUBE_NAMESPACE = 'default'
        KUBE_DEPLOYMENT_NAME = 'energi-node-app'
    }

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(env.DOCKER_IMAGE)
                }
            }
        }

        stage('Push Docker Image to Registry') {
            steps {
                script {
                    docker.withRegistry('myprivate.url', 'docker-credentials') {
                        docker.image(env.DOCKER_IMAGE).push()
                    }
                }
            }
        }

        // Referenced https://github.com/jenkinsci/kubernetes-cd-plugin
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    kubernetesDeploy(
                        kubeconfigId: 'credentials',
                        namespace: env.KUBE_NAMESPACE,
                        yamlFiles: 'energi/*.yaml',
                        dockerCredentials: [
                            [credentialsId: 'docker-credentials', url: 'myprivate.url'],
                        ],
                        
                    )
                }
            }
        }
    }
}