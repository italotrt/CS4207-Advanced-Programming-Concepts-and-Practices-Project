const chat = artifacts.require("Chat");

module.exports = function(deployer) {
    deployer.deploy(chat, 'test');
}

