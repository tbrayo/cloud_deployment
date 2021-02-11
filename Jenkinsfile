pipeline {
  agent any
  stages {
    stage('Checkout Source') {
      steps {
        git url:'https://github.com/gazi-opu/cloud_deployment.git', branch:'main'
      }
    }
      stage("Build image") {
            steps {
                script {
                  service_a = docker.build("abdullah1122/cloud_deployment:service-a-0.0.1-SNAPSHOT cloud_deployment/service-a/")
                  simulator_service = docker.build("abdullah1122/cloud_deployment:service-a-0.0.1-SNAPSHOT cloud_deployment/service-a/")
                }
            }
        }

      stage("Push image") {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'abdullah1122') {
                            service_a.push("latest")
                            service_a.push("${env.BUILD_ID}")

                    docker.withRegistry('https://registry.hub.docker.com', 'abdullah1122') {
                            simulator_service.push("latest")
                            simulator_service.push("${env.BUILD_ID}")
                    }
                }
            }
        }

    stage('Deploy App In Kubernetes') {
      steps {
        script {
          kubernetesDeploy(configs: "service-a-deployment.yml", kubeconfigId: "mykubeconfig")
          kubernetesDeploy(configs: "simulator-service-deployment.yml", kubeconfigId: "mykubeconfig")
        }
      }
    }
  }
}



/*pipeline {

  agent any

  stages {

    stage('Checkout Source') {
      steps {
        git url:'https://github.com/gazi-opu/cloud_deployment.git', branch:'main'
      }
    }
   
      stage("Build image") {
            steps {
                script {
                  myapp = docker.build("abdullah1122/cloud_deployment:service-a-0.0.1-SNAPSHOT cloud_deployment/service-a/")
                }
            }
        }
    /*
      stage("Push image") {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', '24704') {
                            myapp.push("latest")
                            myapp.push("${env.BUILD_ID}")
                    }
                }
            }
        }

    
    /*stage('Deploy App') {
      steps {
        script {
          kubernetesDeploy(configs: "hellowhale.yml", kubeconfigId: "mykubeconfig")
        }
      }
    }*/

  }

}
