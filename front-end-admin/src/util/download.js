import axios from 'axios';

export const downloadFile = async (fileUrl) => {
  try {
    // const response = await axios.get(fileUrl, {
    //   responseType: 'blob',
    // });
    // const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = fileUrl;
    // link.setAttribute('download', 'file.pdf');
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  } catch (error) {
    console.error(error);
  }
};
