pragma solidity >=0.4.22 <0.7.0;

contract Ballot {
   
    bytes32[]  proposals;
    string  candidate;  
    uint  timestamp1;
    string  emailId; 
    address  chairperson;



    // This is a type for a single proposal.


    // This declares a state variable that
    // stores a `Voter` struct for each possible address.
    //mapping(address => Voter)  voters;
    //Voter public  sender = voters[msg.sender];

    // A dynamically-sized array of `Proposal` structs.

    /// Create a new ballot to choose one of `proposalNames`.
    constructor(bytes32[] memory proposalNames) public {
        chairperson = msg.sender;
        proposals=proposalNames;
    }
    
    function stringToBytes32(string memory source) private returns (bytes32 result){
        bytes memory tempEmptyStringTest = bytes(source);
        if (tempEmptyStringTest.length == 0) {
        return 0x0;
        }
        assembly {
            result := mload(add(source, 32))
        }
    }
    
    function checkValidCandidate(bytes32[] memory proposalNames, string  memory proposal) private returns (bool result) {
        bytes32 proposalByte = stringToBytes32(proposal);
        for(uint i=0; i<proposalNames.length; i++){
            if(proposalNames[i]==proposalByte) {
                return true;
            }
        }
        return false;
    }
    

    function vote(string memory proposal, string memory _emailId) public {
        require(msg.sender==chairperson);
        require(checkValidCandidate(proposals,proposal),"Voted candidate should be among list of valid candidates");
        candidate = proposal;
        timestamp1=now;
        emailId=_emailId;
    }
    function getCandidateName() public view returns (string memory result){
        result = candidate;
        return result;
    }
    function getEmaildId() public view returns (string memory result){
        result = emailId;
        return result;
    }
    function getTimestamp() public view returns (uint  result){
        result = timestamp1;
        return result;
    }
    
}
