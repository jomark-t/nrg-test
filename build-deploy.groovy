// Referenced the steps from https://www.jenkins.io/doc/book/pipeline/jenkinsfile/

pipeline {
    agent any

    // Set environment variables
    environment {
        DOCKER_IMAGE = 'jt/e3:1.0'
        KUBE_NAMESPACE = 'default'
        KUBE_DEPLOYMENT_NAME = 'energi-node-app'
    }

    // Build docker image 
    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    def dockerfilePath = 'Dockerfile'

                    // Build the Docker image from dockerfile
                    docker.build(env.DOCKER_IMAGE, "-f ${dockerfilePath} .")
                }
            }
        }

        stage('Push Docker Image to Registry') {
            steps {
                script {
                    // Push built image to target registry/s3
                    docker.withRegistry('myprivate.url', 'docker-credentials') {
                        docker.image(env.DOCKER_IMAGE).push()
                    }
                }
            }
        }

        // Referenced https://github.com/jenkinsci/kubernetes-cd-plugin
        // Haven't really deploy kubernetes using jenkins
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