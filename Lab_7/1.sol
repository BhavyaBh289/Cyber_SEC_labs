pragma solidity ^0.8.0;

contract Voting {
    uint256 public totalVotes;

    string[] public candidates;
    mapping(string => uint256) public votesReceived;

    enum VoteStatus { NotVoted, Voted }

    struct Voter {
        bool isRegistered;
        uint256 votedCandidateIndex;
        VoteStatus voteStatus;
    }
    mapping(address => Voter) public voters;

    constructor(string[] memory _candidates) {
        for (uint256 i = 0; i < _candidates.length; i++) {
            candidates.push(_candidates[i]);
            votesReceived[_candidates[i]] = 0;
        }
        totalVotes = 0;
    }

    function registerVoter() public {
        require(!voters[msg.sender].isRegistered, "You are already registered.");
        voters[msg.sender].isRegistered = true;
        voters[msg.sender].voteStatus = VoteStatus.NotVoted;
    }

    function vote(uint256 candidateIndex) public {
        require(voters[msg.sender].isRegistered, "You are not registered to vote.");
        require(voters[msg.sender].voteStatus == VoteStatus.NotVoted, "You have already voted.");
        require(candidateIndex < candidates.length, "Invalid candidate index.");

        string memory selectedCandidate = candidates[candidateIndex];
        votesReceived[selectedCandidate]++;
        totalVotes++;
        voters[msg.sender].votedCandidateIndex = candidateIndex;
        voters[msg.sender].voteStatus = VoteStatus.Voted;
    }

    function getWinningCandidate() public view returns (string memory) {
        require(totalVotes > 0, "No votes have been cast yet.");

        string memory winningCandidate = candidates[0];
        uint256 maxVotes = votesReceived[candidates[0]];

        for (uint256 i = 1; i < candidates.length; i++) {
            if (votesReceived[candidates[i]] > maxVotes) {
                maxVotes = votesReceived[candidates[i]];
                winningCandidate = candidates[i];
            }
        }

        return winningCandidate;
    }
}

