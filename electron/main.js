const { app, BrowserWindow, Menu, dialog, ipcMain } = require('electron');
const path = require('path');
const axios = require('axios');
const fs = require('fs');

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

  // Listen for save project event from renderer process
  ipcMain.on('save-project', () => {
    saveProject();
  });

  // Listen for export event from renderer process
  ipcMain.on('export', (event, format) => {
    exportFile(format);
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
        console.log('Media selected:', result.filePaths[0]);
        mainWindow.webContents.send('add-media', result.filePaths[0]); // Send file path to renderer process
      }
    })
    .catch((err) => console.error('Error adding media:', err));
}

function saveProject() {
  dialog
    .showSaveDialog(mainWindow, {
      title: 'Save Project',
      defaultPath: path.join(app.getPath('documents'), 'project.gmp'),
      filters: [{ name: 'Project Files', extensions: ['gmp'] }],
    })
    .then((result) => {
      if (!result.canceled) {
        const filePath = result.filePath;
        console.log('Saving project to:', filePath);
        axios.post('http://localhost:8080/export', null, {
          params: {
            format: 'PROJECT',
            filePath: path.dirname(filePath),
            fileName: path.basename(filePath)
          }
        })
        .then(response => {
          console.log('Project saved successfully:', response.data);
          fs.writeFileSync(filePath, response.data);
        })
        .catch(error => {
          console.error('Error saving project:', error.response?.data || error.message);
        });
      }
    })
    .catch((err) => console.error('Error saving project:', err));
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
  dialog
    .showSaveDialog(mainWindow, {
      title: 'Export KML',
      defaultPath: path.join(app.getPath('downloads'), 'export.kml'),
      filters: [{ name: 'KML Files', extensions: ['kml'] }],
    })
    .then((result) => {
      if (!result.canceled) {
        const filePath = result.filePath;
        console.log('Exporting KML to:', filePath);
        axios.post('http://localhost:8080/export', null, {
          params: {
            format: 'KML',
            filePath: path.dirname(filePath),
            fileName: path.basename(filePath)
          }
        })
        .then(response => {
          console.log('KML exported successfully:', response.data);
          fs.writeFileSync(filePath, response.data);
        })
        .catch(error => {
          console.error('Error exporting KML:', error.response?.data || error.message);
        });
      }
    })
    .catch((err) => console.error('Error exporting KML:', err));
}

function exportKMZ() {
  dialog
    .showSaveDialog(mainWindow, {
      title: 'Export KMZ',
      defaultPath: path.join(app.getPath('downloads'), 'export.kmz'),
      filters: [{ name: 'KMZ Files', extensions: ['kmz'] }],
    })
    .then((result) => {
      if (!result.canceled) {
        const filePath = result.filePath;
        console.log('Exporting KMZ to:', filePath);
        axios.post('http://localhost:8080/export', null, {
          params: {
            format: 'KMZ',
            filePath: path.dirname(filePath),
            fileName: path.basename(filePath)
          }
        })
        .then(response => {
          console.log('KMZ exported successfully:', response.data);
          fs.writeFileSync(filePath, response.data);
        })
        .catch(error => {
          console.error('Error exporting KMZ:', error.response?.data || error.message);
        });
      }
    })
    .catch((err) => console.error('Error exporting KMZ:', err));
}

function exportFile(format) {
  dialog
    .showSaveDialog(mainWindow, {
      title: `Export ${format}`,
      defaultPath: path.join(app.getPath('downloads'), `export.${format.toLowerCase()}`),
      filters: [{ name: `${format} Files`, extensions: [format.toLowerCase()] }],
    })
    .then((result) => {
      if (!result.canceled) {
        const filePath = result.filePath;
        console.log(`Exporting ${format} to:`, filePath);
        axios.post('http://localhost:8080/export', null, {
          params: {
            format: format,
            filePath: path.dirname(filePath),
            fileName: path.basename(filePath)
          }
        })
        .then(response => {
          console.log(`${format} exported successfully:`, response.data);
          fs.writeFileSync(filePath, response.data);
        })
        .catch(error => {
          if (error.response && error.response.data.includes('ExistsException')) {
            console.error(`Error exporting ${format}: File already exists.`);
          } else {
            console.error(`Error exporting ${format}:`, error.response?.data || error.message);
          }
        });
      }
    })
    .catch((err) => console.error(`Error exporting ${format}:`, err));
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
