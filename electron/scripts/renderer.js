const axios = require('axios');
const fs = require('fs');

function loadMainApp() {
  fetch('./views/main-app.html')
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.text();
    })
    .then((html) => {
      document.getElementById('app').innerHTML = html;
    })
    .catch((error) => {
      console.error('Error loading main-app.html:', error);
      document.getElementById('app').innerHTML =
        `<p>Failed to load content. Error: ${error.message}</p>`;
    });
}

window.electronAPI.addMedia((event, filePath) => {
  const imageElement = document.getElementById('selected-image');
  if (imageElement) {
    imageElement.src = `file://${filePath}`;
  } else {
    console.error('Image element not found in the DOM.');
  }
});


function addMedia() {
  dialog
    .showOpenDialog(mainWindow, {
      properties: ['openFile'],
      filters: [{ name: 'Media Files', extensions: ['jpg', 'png', 'mp4', 'avi'] }],
    })
    .then((result) => {
      if (!result.canceled) {
        const filePath = result.filePaths[0];
        console.log('Media added:', filePath);

        // Create a FormData instance
        const formData = new FormData();
        formData.append('file', fs.createReadStream(filePath));

        // Send the file to the backend
        axios
          .post('http://localhost:8080/project/upload', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          })
          .then((response) => {
            console.log('File uploaded successfully:', response.data);
            mainWindow.webContents.send('media-uploaded', response.data);
          })
          .catch((error) => {
            console.error('Error uploading file:', error.message);
            dialog.showErrorBox('Upload Failed', `Could not upload file: ${error.message}`);
          });
      }
    })
    .catch((err) => console.error('Error adding media:', err));
}

loadMainApp();
