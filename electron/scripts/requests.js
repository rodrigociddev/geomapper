const axios = require('axios');

const baseUrl = 'http://localhost:8080'

// Add media request
function addMediaRequest(filePath){

    const params = new URLSearchParams();
    params.append('filePath', filePath);

    const mediaResponse = 
    axios.post( `${baseUrl}/project/upload`,
        params,
        {
            headers:{'Content-Type': 'application/x-www-form-urlencoded'}
        }
    )
    .then((response)=>{
        media=response.data;
        console.log('Uploaded Media:', media);
    })
    .catch((error)=>{
        console.error('Error uploading media:', error.response?.data || error.message);
        throw error;
    })

    return mediaResponse;
}
module.exports={
    addMediaRequest
}