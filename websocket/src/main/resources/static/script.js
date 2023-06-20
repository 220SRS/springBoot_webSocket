const chatbox = document.getElementById('chatbox');
const messageInput = document.getElementById('messageInput');
const sendButton = document.getElementById('sendButton');

const socket = new WebSocket('ws://localhost:8080/chat');

socket.onopen = function() {
  console.log('WebSocket 연결이 열렸습니다.');

  sendButton.addEventListener('click', sendMessage);
  messageInput.addEventListener('keypress', function(event) {
    if (event.keyCode === 13) {
      sendMessage();
    }
  });
};

socket.onmessage = function(event) {
  const message = event.data;
  displayMessage(message);
};

socket.onclose = function() {
  console.log('WebSocket 연결이 닫혔습니다.');
};

function sendMessage() {
  const message = messageInput.value;
  socket.send(message);
  messageInput.value = '';
}

function displayMessage(message, isSentMessage) {
  const messageElement = document.createElement('p');
  messageElement.textContent = message;

  if (isSentMessage) {
    messageElement.classList.add('sent-message');
  } else {
    messageElement.classList.add('received-message');
  }

  chatbox.appendChild(messageElement);
  chatbox.scrollTop = chatbox.scrollHeight;
}

