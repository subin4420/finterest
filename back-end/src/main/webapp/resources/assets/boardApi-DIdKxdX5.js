import{i as o}from"./index-CFW9R_hc.js";const n="/api/board",s={async getList(a){const{data:t}=await o.get(n,{params:a});return console.log("BOARD GET LIST: ",t),t},async get(a){const{data:t}=await o.get(`${n}/${a}`);return console.log("BOARD GET",t),t},async create(a){const{data:t}=await o.post(n,a);return console.log("BOARD POST: ",t),t},async update(a){const{data:t}=await o.put(`${n}/${a.no}`,a);return console.log("BOARD PUT: ",t),t},async delete(a){const{data:t}=await o.delete(`${n}/${a}`);return console.log("BOARD DELETE: ",t),t}};export{s as b};
