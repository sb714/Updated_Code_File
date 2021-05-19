/*package com.FileSystemExample.FileSystem.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity(name = "FileDocument")
@Table(name = "FILE_DOCUMENT")
@Data
@ToString
public class FileDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "FILENAME")
	private String fileName;

	@Column(name = "DOCFILE")
	@Lob
	private byte[] docFile;
}
*/


