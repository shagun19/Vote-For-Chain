/*
	The following is an extremely basic example of a solidity contract.
	It takes a string upon creation and then repeats it when greet() is called.
*/

pragma solidity ^0.5.6;

contract Greeter {
    function constuctor(string memory _greeting) public {
        creator = msg.sender;
        greeting = _greeting;
    }

    function greet() public view returns (string memory) {
        return greeting;
    }
    function getBlockNumber() public view returns (uint) {
        return block.number;
    }

    function setGreeting(string memory _newgreeting) public {
        greeting = _newgreeting;
    }

     /**********
     Standard kill() function to recover funds
     **********/

  /*  function kill() public {
        if (msg.sender == creator)
        // only allow this action if the account sending the signal is the creator
            selfdestruct(creator);
            // kills this contract and sends remaining funds back to creator
    }*/
}