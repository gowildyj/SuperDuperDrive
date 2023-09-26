package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTest extends CloudStorageApplicationTests {

	@Test
	public void testDelete() {
		String noteTitle = "test1";
		String noteDescription = "123";
		HomeTest homeTest = signUpAndLogin();
		createNote(noteTitle, noteDescription, homeTest);
		homeTest.navToNotesTab();
		homeTest = new HomeTest(driver);
		Assertions.assertFalse(homeTest.noNotes(driver));
		deleteNote(homeTest);
		Assertions.assertTrue(homeTest.noNotes(driver));
	}

	private void deleteNote(HomeTest homeTest) {
		homeTest.deleteNote();
		ResultTest resultTest = new ResultTest(driver);
		resultTest.clickOk();
	}

	@Test
	public void testCreateAndDisplay() {
		String noteTitle = "test1";
		String noteDescription = "123";
		HomeTest homeTest = signUpAndLogin();
		createNote(noteTitle, noteDescription, homeTest);
		homeTest.navToNotesTab();
		homeTest = new HomeTest(driver);
		Note note = homeTest.getFirstNote();
		Assertions.assertEquals(noteTitle, note.getNoteTitle());
		Assertions.assertEquals(noteDescription, note.getNoteDescription());
		deleteNote(homeTest);
		homeTest.logout();
	}

	@Test
	public void testModify() {
		String noteTitle = "test1";
		String noteDescription = "123";
		HomeTest homeTest = signUpAndLogin();
		createNote(noteTitle, noteDescription, homeTest);
		homeTest.navToNotesTab();
		homeTest = new HomeTest(driver);
		homeTest.editNote();
		String modifiedNoteTitle = "test1-edit";
		homeTest.modifyNoteTitle(modifiedNoteTitle);
		String modifiedNoteDescription = "321";
		homeTest.modifyNoteDescription(modifiedNoteDescription);
		homeTest.saveNoteChanges();
		ResultTest resultTest = new ResultTest(driver);
		resultTest.clickOk();
		homeTest.navToNotesTab();
		Note note = homeTest.getFirstNote();
		Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
		Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
	}

	private void createNote(String noteTitle, String noteDescription, HomeTest homeTest) {
		homeTest.navToNotesTab();
		homeTest.addNewNote();
		homeTest.setNoteTitle(noteTitle);
		homeTest.setNoteDescription(noteDescription);
		homeTest.saveNoteChanges();
		ResultTest resultTest = new ResultTest(driver);
		resultTest.clickOk();
		homeTest.navToNotesTab();
	}
}
