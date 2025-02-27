package com.grabit.app.dtoTests;

import com.grabit.app.dto.ProjectLeaderboardDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProjectLeaderboardDTOTests {

    @Test
    public void testLeaderboardGettersAndSetters() {
        ProjectLeaderboardDTO dto = new ProjectLeaderboardDTO(5, 23, "exampleUser23", 20);

        assertEquals(5, dto.getPosition());
        assertEquals(23, dto.getUserID());
        assertEquals("exampleUser23", dto.getGithubID());
        assertEquals(20, dto.getTotalScore());

        dto.setPosition(11);
        dto.setUserID(23);
        dto.setGithubID("updatedExampleUser23");
        dto.setTotalScore(35);

        assertEquals(11, dto.getPosition());
        assertEquals(23, dto.getUserID());
        assertEquals("updatedExampleUser23", dto.getGithubID());
        assertEquals(35, dto.getTotalScore());
    }
}
