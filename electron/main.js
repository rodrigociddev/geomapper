const { app, BrowserWindow, Menu, dialog, ipcMain } = require('electron');
const path = require('path');

let mainWindow;

app.on('ready', () => {
  mainWindow = new BrowserWindow({
    width: 1200,
    height: 800,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      contextIsolation: true,
      enableRemoteModule: false,
      sandbox:false
    },
  });

  mainWindow.loadFile('index.html');

  // Set custom menu
  const menu = Menu.buildFromTemplate(createMenuTemplate());
  Menu.setApplicationMenu(menu);

  // Listen for Add Media dialog event from renderer process
  ipcMain.on('open-add-media-dialog', () => {
    addMedia();
  });
});

function createMenuTemplate() {
  return [
    {
      label: 'File',
      submenu: [
        { label: 'Add Media', click: () => addMedia() },
        { label: 'Save Project', click: () => saveProject() },
        { label: 'Open Project', click: () => openProject() },
        { label: 'New Project', click: () => newProject() },
        { type: 'separator' },
        { label: 'Exit', role: 'quit' },
      ],
    },
    {
      label: 'Edit',
      submenu: [
        { label: 'Undo', role: 'undo' },
        { label: 'Redo', role: 'redo' },
        { type: 'separator' },
        { label: 'Select All', click: () => selectAll() },
        { label: 'Unselect All', click: () => unselectAll() },
        { label: 'Delete Selected', click: () => deleteSelected() },
      ],
    },
    {
      label: 'View', // Keep the default View menu functionality
      submenu: [
        { label: 'Reload', role: 'reload' },
        { label: 'Force Reload', role: 'forceReload' },
        { label: 'Toggle Developer Tools', role: 'toggleDevTools' },
        { type: 'separator' },
        { label: 'Actual Size', role: 'resetZoom' },
        { label: 'Zoom In', role: 'zoomIn' },
        { label: 'Zoom Out', role: 'zoomOut' },
        { type: 'separator' },
        { label: 'Toggle Fullscreen', role: 'togglefullscreen' },
      ],
    },
    {
      label: 'Window',
      submenu: [
        { label: 'Minimize', role: 'minimize' },
        { label: 'Close', role: 'close' },
      ],
    },
    {
      label: 'Export',
      submenu: [
        { label: 'Export KML', click: () => exportKML() },
        { label: 'Export KMZ', click: () => exportKMZ() },
      ],
    },
    {
      label: 'Help',
      submenu: [
        {
          label: 'About',
          click: () => showAboutDialog(),
        },
      ],
    },
  ];
}

// Centralized Add Media function
function addMedia() {
  dialog
    .showOpenDialog(mainWindow, {
      properties: ['openFile'],
      filters: [{ name: 'Media Files', extensions: ['jpg', 'png', 'mp4', 'avi'] }],
    })
    .then((result) => {
      if (!result.canceled) {
        console.log('Media added:', result.filePaths[0]);
        mainWindow.webContents.send('add-media', result.filePaths[0]); // Send file path to renderer process
      }
    })
    .catch((err) => console.error('Error adding media:', err));
}

function saveProject() {
  console.log('Save Project clicked');
  // Logic to save the project
}

function openProject() {
  dialog
    .showOpenDialog(mainWindow, {
      properties: ['openFile'],
      filters: [{ name: 'Project Files', extensions: ['json', 'proj'] }],
    })
    .then((result) => {
      if (!result.canceled) {
        console.log('Project opened:', result.filePaths[0]);
        mainWindow.webContents.send('open-project', result.filePaths[0]);
      }
    })
    .catch((err) => console.error('Error opening project:', err));
}

function newProject() {
  console.log('New Project created');
  mainWindow.webContents.send('new-project');
}

function selectAll() {
  console.log('Select All clicked');
  mainWindow.webContents.send('select-all');
}

function unselectAll() {
  console.log('Unselect All clicked');
  mainWindow.webContents.send('unselect-all');
}

function deleteSelected() {
  console.log('Delete Selected clicked');
  mainWindow.webContents.send('delete-selected');
}

function exportKML() {
  console.log('Export KML clicked');
  mainWindow.webContents.send('export', { format: 'KML' });
}

function exportKMZ() {
  console.log('Export KMZ clicked');
  mainWindow.webContents.send('export', { format: 'KMZ' });
}

function showAboutDialog() {
  dialog.showMessageBox(mainWindow, {
    type: 'info',
    buttons: ['OK'],
    title: 'About',
    message: 'GeoTagR',
    detail: 'Version: 1.0.0\nAuthor: Your Name',
  });
}
