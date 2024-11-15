const { contextBridge, ipcRenderer } = require('electron');

contextBridge.exposeInMainWorld('electronAPI', {
  openFileDialog: async () => {
    return await ipcRenderer.invoke('dialog:openFile');
  },
  addMedia: (callback) => ipcRenderer.on('add-media', callback),
  addMediaDialog: () => ipcRenderer.send('open-add-media-dialog'),
  selectAll: (callback) => ipcRenderer.on('select-all', callback),
  unselectAll: (callback) => ipcRenderer.on('unselect-all', callback),
  deleteSelected: (callback) => ipcRenderer.on('delete-selected', callback),
});
