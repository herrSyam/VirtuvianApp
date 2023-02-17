package com.example.virtuvianapplication.response;

import com.example.virtuvianapplication.model.FileModel;

public class FileResponse {
    private FileModel[] data;

    public FileModel[] getData() {
        return data;
    }

    public void setData(FileModel[] data) {
        this.data = data;
    }
}
