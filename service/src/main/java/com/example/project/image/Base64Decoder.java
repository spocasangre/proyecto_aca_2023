/*
 * Copyright 2023 WeGotYou!
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.project.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64Decoder implements MultipartFile {
	
    private final byte[] IMAGE;

    private final String HEADER;

    private Base64Decoder(byte[]image,String header){
        this.IMAGE = image;
        this.HEADER = header;
    }

    public static MultipartFile multipartFile(byte[]image,String header){
        return new Base64Decoder(image,header);
    }

	@Override
	public String getName() {
		return System.currentTimeMillis()+Math.random()+"."+HEADER.split("/")[1].split(";")[0];
	}

	@Override
	public String getOriginalFilename() {
		return System.currentTimeMillis()+(int)Math.random()*10000+"."+HEADER.split("/")[1].split(";")[0];
	}

	@Override
	public String getContentType() {
		return HEADER.split(":")[1];
	}

	@Override
	public boolean isEmpty() {
		return IMAGE == null || IMAGE.length == 0;
	}

	@Override
	public long getSize() {
		return IMAGE.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return IMAGE;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(IMAGE);
	}

	@SuppressWarnings("resource")
	@Override
	public void transferTo(File file) throws IOException, IllegalStateException {
		new FileOutputStream(file).write(IMAGE);	
	}

}
