defaultBuildSettings()

node('essent-dedicated') {
  defaultMavenProject(
    essent: [
      verifyPomIntegrity: true
    ]
  ) {
    stage('mvn') {
      sh "mvn clean deploy site:site site:stage ${sonarMvnGoal()} -DskipTests"
    }

    if (env.BRANCH_NAME == 'develop') {
      stage('publish-site') {
        buildSiteImage dir: 'target/staging/essent-selenium-automated-testing/', tag: 'bhcr-essent/doc/essent-selenium-automated-testing'
        build job: '/BillingHouse/Essent/deploy-essent-doc-site/master', wait: false
      }
    }
  }
}
