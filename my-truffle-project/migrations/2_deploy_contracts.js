// const SimpleStroage = artifacts.require("SimpleStorage");
//
// module.exports = function(deployer) {
//     deployer.deploy(SimpleStroage, 'Hello Blockchain!');
// }
//
const enrollmentStorage = artifacts.require("EnrollmentContract");

module.exports = function(deployer) {
    deployer.deploy(enrollmentStorage, 'Hello Blockchain!');
}

