const axios = require('axios');

const baseUrl = 'http://localhost:8080'

// Add media request
function addMediaRequest(filePath){

    const params = new URLSearchParams();
    params.append('filePath', filePath);

    return axios.post( `${baseUrl}/project/upload`,
        params,
        {
            headers:{'Content-Type': 'application/x-www-form-urlencoded'}
        }
    )
    .then((response)=>{
        media=response.data;
        console.log('Uploaded Media:', media);
        return media;
    })
    .catch((error)=>{
        console.error('Error uploading media:', error.response?.data || error.message);
        throw error;
    })

    
}

function deleteMediaRequest(uuids){
    axios.delete(`${baseUrl}/project/batch-delete`, {
                data: uuids, // Pass the array of IDs in the request body
    })
    .then((response)=>{
        console.log("batch delete sent")
    })
    .catch((error)=>{
        if (error.response) {
            console.error('Batch delete failed:', error.response.data);
        } else {
            console.error('Error:', error.message);
        }
    })
}
        

module.exports={
    addMediaRequest,
    deleteMediaRequest
}