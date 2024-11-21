// Keep track of media items
const mediaItems = [];

// Function to show default message
function showDefaultMessage() {
  document.getElementById('default-message').style.display = 'block';
  document.getElementById('selected-image').style.display = 'none';
  document.getElementById('media-details').style.display = 'none';
}

// Function to hide default message and show media details
function showMediaDetails() {
  document.getElementById('default-message').style.display = 'none';
  document.getElementById('selected-image').style.display = 'block';
  document.getElementById('media-details').style.display = 'block';
}

// Centralized function to handle adding media
function handleAddMedia(filePath) {

  const imageElement = document.getElementById('selected-image');

  window.requestsAPI.addMediaRequest(filePath)
    .then(media => {

      const { id, name, latitude, longitude, annotations } = media;
      if (imageElement) {
        // Update the selected image display
        imageElement.src = `file://${filePath}`;
        showMediaDetails(); // Show media details in the main section
    
        // Create a new media object
        const newMedia = {
          id: id,
          filePath: `file://${filePath}`,
          title: name,
          latitude: latitude,
          longitude: longitude,
          annotations: annotations,
        };
    
        mediaItems.push(newMedia); // Add to media items array
        renderMediaList(); // Re-render the sidebar
        selectMedia(id) // select newly added media
      } else {
        console.error('Image element not found in the DOM.');
      }
    })
}


// Listen for the 'add-media' event from the main process
window.electronAPI.addMedia((event, filePath) => {
  if (filePath) {
    handleAddMedia(filePath);
  } else {
    console.error('No file path received from the main process.');
  }
});


// Handle the Add Media button click
document.getElementById('add-media-button').addEventListener('click', () => {
  window.electronAPI.addMediaDialog();
});

// Render media list in the sidebar
function renderMediaList() {
  const mediaList = document.getElementById('media-list');
  mediaList.innerHTML = ''; // Clear existing content

  if (mediaItems.length === 0) {
    showDefaultMessage(); // If no media, show the default message
  }

  mediaItems.forEach((media, index) => {
    // media.id = index; // Update media ID to match its new index

    // Create a media block
    const mediaBlock = document.createElement('div');
    mediaBlock.classList.add('d-flex', 'align-items-start', 'my-3', 'media-block', 'text-light-blue');
    mediaBlock.dataset.id = media.id;

    const img = document.createElement('img');
    img.src = media.filePath;
    img.classList.add('img-thumbnail', 'me-2');
    img.style.width = '50px';
    img.style.height = '50px';

    const mediaInfo = document.createElement('div');
    const mediaText = document.createElement('p');
    mediaText.classList.add('mb-1');
    mediaText.innerText = media.title;

    mediaInfo.appendChild(mediaText);
    mediaBlock.appendChild(img);
    mediaBlock.appendChild(mediaInfo);

    // Add click listener to select the media
    mediaBlock.addEventListener('click', () => selectMedia(media.id));

    mediaList.appendChild(mediaBlock);
  });
}

let currentSelectedMediaId = null;
let changesMade = false;

// Select a media block and display its details
function selectMedia(mediaId) {
  const selectedMedia = mediaItems.find((media) => media.id === mediaId);

  if (selectedMedia) {
    currentSelectedMediaId = mediaId; // Track the currently selected media ID

    // Update the main content area
    document.getElementById('selected-image').src = selectedMedia.filePath;
    document.getElementById('title').value = selectedMedia.title;
    document.getElementById('latitude').value = selectedMedia.latitude;
    document.getElementById('longitude').value = selectedMedia.longitude;
    document.getElementById('annotations').value = selectedMedia.annotations;

    showMediaDetails(); // Show media details

    // Highlight the selected block in the sidebar
    document.querySelectorAll('.media-block').forEach((block) => {
      block.classList.remove('bg-primary', 'text-white');
    });

    const selectedBlock = document.querySelector(`.media-block[data-id="${mediaId}"]`);
    if (selectedBlock) {
      selectedBlock.classList.add('bg-primary', 'text-white');
    }
  }
}

// Delete Selected: Remove highlighted blocks
window.electronAPI.deleteSelected(() => {
  // Get all highlighted blocks
  const selectedBlocks = document.querySelectorAll('.media-block.bg-primary');

  selectedBlocks.forEach((block) => {
    const mediaId = parseInt(block.dataset.id, 10);

    // Remove the block from the DOM
    block.remove();

    // Remove the media item from the array
    const mediaIndex = mediaItems.findIndex((media) => media.id === mediaId);
    if (mediaIndex > -1) {
      mediaItems.splice(mediaIndex, 1);
    }
  });

  // Re-render the sidebar to update indices and data attributes
  renderMediaList();

  // If no media is left, show the default message
  if (mediaItems.length === 0) {
    showDefaultMessage();
  }
});

// Select All: Highlight all sidebar blocks
window.electronAPI.selectAll(() => {
    document.querySelectorAll('.media-block').forEach((block) => {
        block.classList.add('bg-primary', 'text-white');
    });
});

// Unselect All: Remove highlighting from all sidebar blocks
window.electronAPI.unselectAll(() => {
    document.querySelectorAll('.media-block').forEach((block) => {
        block.classList.remove('bg-primary', 'text-white');
    });
});

// Function to update media details
function updateMediaDetails(mediaId, field, value) {
  const media = mediaItems.find((media) => media.id === mediaId);
  if (media) {
    media[field] = value;
    changesMade = true;
    document.getElementById('save-changes-button').classList.add('active');
    console.log(`Updated ${field} for media ID ${mediaId} to ${value}`);
  }
}

// Function to call the backend to update media details
function updateMediaOnBackend(mediaId) {
  const media = mediaItems.find((media) => media.id === mediaId);
  if (!media) return;

  const requests = [
    fetch(`/media/rename/${mediaId}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ newName: media.title })
    }),
    fetch(`/media/latitude/${mediaId}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ latitude: media.latitude })
    }),
    fetch(`/media/longitude/${mediaId}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ longitude: media.longitude })
    }),
    fetch(`/media/annotate/${mediaId}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ annotation: media.annotations })
    })
  ];

  Promise.all(requests)
    .then(responses => {
      responses.forEach(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
      });
      console.log('Success: Media details updated');
      changesMade = false;
      document.getElementById('save-changes-button').classList.remove('active');
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

// Add event listeners to track changes
document.getElementById('title').addEventListener('input', (event) => {
  if (currentSelectedMediaId !== null) {
    updateMediaDetails(currentSelectedMediaId, 'title', event.target.value);
  }
});

document.getElementById('latitude').addEventListener('input', (event) => {
  if (currentSelectedMediaId !== null) {
    updateMediaDetails(currentSelectedMediaId, 'latitude', event.target.value);
  }
});

document.getElementById('longitude').addEventListener('input', (event) => {
  if (currentSelectedMediaId !== null) {
    updateMediaDetails(currentSelectedMediaId, 'longitude', event.target.value);
  }
});

document.getElementById('annotations').addEventListener('input', (event) => {
  if (currentSelectedMediaId !== null) {
    updateMediaDetails(currentSelectedMediaId, 'annotations', event.target.value);
  }
});

// Handle the Save Media Changes button click
document.getElementById('save-changes-button').addEventListener('click', () => {
  if (changesMade && currentSelectedMediaId !== null) {
    console.log(`Saving changes for media ID ${currentSelectedMediaId}`);
    updateMediaOnBackend(currentSelectedMediaId);
  }
});
