package com.grabit.app.dataTransferObjectTests;

import org.junit.jupiter.api.Test;

import com.grabit.app.dataTransferObject.ProjectLeaderboardDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectLeaderboardDTOTests {

    @Test
    public void testGettersAndSetters () {
        ProjectLeaderboardDTO dto = new ProjectLeaderboardDTO(5, 23, "exampleUser23", 20);

        assertEquals(5, dto.getPosition());
        assertEquals(23, dto.getUserId());
        assertEquals("exampleUser23", dto.getGithubId());
        assertEquals(20, dto.getTotalScore());

        dto.setPosition(11);
        dto.setUserId(23);
        dto.setGithubId("updatedExampleUser23");
        dto.setTotalScore(35);

        assertEquals(11, dto.getPosition());
        assertEquals(23, dto.getUserId());
        assertEquals("updatedExampleUser23", dto.getGithubId());
        assertEquals(35, dto.getTotalScore());
    }
}
