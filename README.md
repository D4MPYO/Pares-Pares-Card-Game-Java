# Pares-Pares-Card-Game-Java

ActivityParesPares: A Card Pairing Game

This is a two-player card game where players attempt to form pairs of cards of the same rank. The game utilizes a standard deck of cards consisting of 52 cards with four suits (Hearts, Diamonds, Clubs, and Spades) and thirteen ranks (2 through Ace).

# Key Features:
  * Deck Initialization: The deck is initialized with all possible card combinations of the four suits and thirteen ranks.
  * Card Shuffling: The deck is shuffled randomly to ensure a fair game.
  * Card Allocation: The deck is split equally between the two players.
  * Pairing Cards: Players try to form pairs of cards of the same rank. The game automatically checks for pairs as the players take their turns.
  * Coin Toss: A coin toss is used to determine which player goes first.
  * Gameplay: During each turn, the current player can either drop a pair of cards they have or draw a card from their opponent’s hand. The goal is to pair cards and reduce the hand size while trying to block the opponent’s progress.
  * Human vs Computer: The game includes a human player (Player 1) and a computer player (Player 2). The human player chooses their actions, while the computer player randomly selects its moves.
  * End of Game: The game ends when one player runs out of cards. The other player wins.

# Game Flow:
  * The deck is initialized and shuffled.
  * Each player receives half the deck.
  * The game proceeds with alternating turns between the human player and the computer.
  * During their turn, the player must either drop a pair of cards or draw a card from the opponent.
  * The game checks for valid pairs after each action.
  * The game continues until one player has no cards left.
