package com.ss.goldentown.web.rest;

import com.ss.goldentown.Goldentown2App;

import com.ss.goldentown.domain.Board;
import com.ss.goldentown.repository.BoardRepository;
import com.ss.goldentown.service.BoardService;
import com.ss.goldentown.repository.search.BoardSearchRepository;
import com.ss.goldentown.service.dto.BoardDTO;
import com.ss.goldentown.service.mapper.BoardMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ss.goldentown.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BoardResource REST controller.
 *
 * @see BoardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goldentown2App.class)
public class BoardResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PRODUCTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PRODUCTION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PURCHASE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PURCHASE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PRODUCTION_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTION_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_SURROUNDING = "AAAAAAAAAA";
    private static final String UPDATED_SURROUNDING = "BBBBBBBBBB";

    private static final String DEFAULT_TEA_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_TEA_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_DRINKING_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_DRINKING_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_MOOD = "AAAAAAAAAA";
    private static final String UPDATED_MOOD = "BBBBBBBBBB";

    private static final String DEFAULT_TEA_SET = "AAAAAAAAAA";
    private static final String UPDATED_TEA_SET = "BBBBBBBBBB";

    private static final String DEFAULT_TEA_PIC = "AAAAAAAAAA";
    private static final String UPDATED_TEA_PIC = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_PIC = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_PIC = "BBBBBBBBBB";

    private static final String DEFAULT_STORAGE_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_STORAGE_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_SHAPE = "AAAAAAAAAA";
    private static final String UPDATED_SHAPE = "BBBBBBBBBB";

    private static final String DEFAULT_AROMA = "AAAAAAAAAA";
    private static final String UPDATED_AROMA = "BBBBBBBBBB";

    private static final String DEFAULT_HOT_AROMA = "AAAAAAAAAA";
    private static final String UPDATED_HOT_AROMA = "BBBBBBBBBB";

    private static final String DEFAULT_TEA_WASH = "AAAAAAAAAA";
    private static final String UPDATED_TEA_WASH = "BBBBBBBBBB";

    private static final String DEFAULT_BOILING_AROMA = "AAAAAAAAAA";
    private static final String UPDATED_BOILING_AROMA = "BBBBBBBBBB";

    private static final String DEFAULT_CUP_AROMA = "AAAAAAAAAA";
    private static final String UPDATED_CUP_AROMA = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_BREW_PIC = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_BREW_PIC = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_BREWING_SOUP = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_BREWING_SOUP = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_BREW_AROMA = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_BREW_AROMA = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_BREW_MOOD = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_BREW_MOOD = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_BREW_PIC = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_BREW_PIC = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_BREW_SOUP = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_BREW_SOUP = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_BREW_AROMA = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_BREW_AROMA = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_BREW_MOOD = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_BREW_MOOD = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_BREW_PIC = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_BREW_PIC = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_BREW_SOUP = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_BREW_SOUP = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_BREW_AROMA = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_BREW_AROMA = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_BREW_MOOD = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_BREW_MOOD = "BBBBBBBBBB";

    private static final String DEFAULT_BREW_TIMES = "AAAAAAAAAA";
    private static final String UPDATED_BREW_TIMES = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Inject
    private BoardRepository boardRepository;

    @Inject
    private BoardMapper boardMapper;

    @Inject
    private BoardService boardService;

    @Inject
    private BoardSearchRepository boardSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBoardMockMvc;

    private Board board;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BoardResource boardResource = new BoardResource();
        ReflectionTestUtils.setField(boardResource, "boardService", boardService);
        this.restBoardMockMvc = MockMvcBuilders.standaloneSetup(boardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Board createEntity(EntityManager em) {
        Board board = new Board()
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE)
                .productionDate(DEFAULT_PRODUCTION_DATE)
                .purchaseTime(DEFAULT_PURCHASE_TIME)
                .productionPlace(DEFAULT_PRODUCTION_PLACE)
                .purchasePlace(DEFAULT_PURCHASE_PLACE)
                .provider(DEFAULT_PROVIDER)
                .process(DEFAULT_PROCESS)
                .time(DEFAULT_TIME)
                .place(DEFAULT_PLACE)
                .surrounding(DEFAULT_SURROUNDING)
                .teaPerson(DEFAULT_TEA_PERSON)
                .drinkingPerson(DEFAULT_DRINKING_PERSON)
                .mood(DEFAULT_MOOD)
                .teaSet(DEFAULT_TEA_SET)
                .teaPic(DEFAULT_TEA_PIC)
                .packagePic(DEFAULT_PACKAGE_PIC)
                .storageMethod(DEFAULT_STORAGE_METHOD)
                .shape(DEFAULT_SHAPE)
                .aroma(DEFAULT_AROMA)
                .hotAroma(DEFAULT_HOT_AROMA)
                .teaWash(DEFAULT_TEA_WASH)
                .boilingAroma(DEFAULT_BOILING_AROMA)
                .cupAroma(DEFAULT_CUP_AROMA)
                .firstBrewPic(DEFAULT_FIRST_BREW_PIC)
                .firstBrewingSoup(DEFAULT_FIRST_BREWING_SOUP)
                .firstBrewAroma(DEFAULT_FIRST_BREW_AROMA)
                .firstBrewMood(DEFAULT_FIRST_BREW_MOOD)
                .secondBrewPic(DEFAULT_SECOND_BREW_PIC)
                .secondBrewSoup(DEFAULT_SECOND_BREW_SOUP)
                .secondBrewAroma(DEFAULT_SECOND_BREW_AROMA)
                .secondBrewMood(DEFAULT_SECOND_BREW_MOOD)
                .thirdBrewPic(DEFAULT_THIRD_BREW_PIC)
                .thirdBrewSoup(DEFAULT_THIRD_BREW_SOUP)
                .thirdBrewAroma(DEFAULT_THIRD_BREW_AROMA)
                .thirdBrewMood(DEFAULT_THIRD_BREW_MOOD)
                .brewTimes(DEFAULT_BREW_TIMES)
                .comment(DEFAULT_COMMENT);
        return board;
    }

    @Before
    public void initTest() {
        boardSearchRepository.deleteAll();
        board = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoard() throws Exception {
        int databaseSizeBeforeCreate = boardRepository.findAll().size();

        // Create the Board
        BoardDTO boardDTO = boardMapper.boardToBoardDTO(board);

        restBoardMockMvc.perform(post("/api/boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardDTO)))
            .andExpect(status().isCreated());

        // Validate the Board in the database
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).hasSize(databaseSizeBeforeCreate + 1);
        Board testBoard = boardList.get(boardList.size() - 1);
        assertThat(testBoard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBoard.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBoard.getProductionDate()).isEqualTo(DEFAULT_PRODUCTION_DATE);
        assertThat(testBoard.getPurchaseTime()).isEqualTo(DEFAULT_PURCHASE_TIME);
        assertThat(testBoard.getProductionPlace()).isEqualTo(DEFAULT_PRODUCTION_PLACE);
        assertThat(testBoard.getPurchasePlace()).isEqualTo(DEFAULT_PURCHASE_PLACE);
        assertThat(testBoard.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testBoard.getProcess()).isEqualTo(DEFAULT_PROCESS);
        assertThat(testBoard.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testBoard.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testBoard.getSurrounding()).isEqualTo(DEFAULT_SURROUNDING);
        assertThat(testBoard.getTeaPerson()).isEqualTo(DEFAULT_TEA_PERSON);
        assertThat(testBoard.getDrinkingPerson()).isEqualTo(DEFAULT_DRINKING_PERSON);
        assertThat(testBoard.getMood()).isEqualTo(DEFAULT_MOOD);
        assertThat(testBoard.getTeaSet()).isEqualTo(DEFAULT_TEA_SET);
        assertThat(testBoard.getTeaPic()).isEqualTo(DEFAULT_TEA_PIC);
        assertThat(testBoard.getPackagePic()).isEqualTo(DEFAULT_PACKAGE_PIC);
        assertThat(testBoard.getStorageMethod()).isEqualTo(DEFAULT_STORAGE_METHOD);
        assertThat(testBoard.getShape()).isEqualTo(DEFAULT_SHAPE);
        assertThat(testBoard.getAroma()).isEqualTo(DEFAULT_AROMA);
        assertThat(testBoard.getHotAroma()).isEqualTo(DEFAULT_HOT_AROMA);
        assertThat(testBoard.getTeaWash()).isEqualTo(DEFAULT_TEA_WASH);
        assertThat(testBoard.getBoilingAroma()).isEqualTo(DEFAULT_BOILING_AROMA);
        assertThat(testBoard.getCupAroma()).isEqualTo(DEFAULT_CUP_AROMA);
        assertThat(testBoard.getFirstBrewPic()).isEqualTo(DEFAULT_FIRST_BREW_PIC);
        assertThat(testBoard.getFirstBrewingSoup()).isEqualTo(DEFAULT_FIRST_BREWING_SOUP);
        assertThat(testBoard.getFirstBrewAroma()).isEqualTo(DEFAULT_FIRST_BREW_AROMA);
        assertThat(testBoard.getFirstBrewMood()).isEqualTo(DEFAULT_FIRST_BREW_MOOD);
        assertThat(testBoard.getSecondBrewPic()).isEqualTo(DEFAULT_SECOND_BREW_PIC);
        assertThat(testBoard.getSecondBrewSoup()).isEqualTo(DEFAULT_SECOND_BREW_SOUP);
        assertThat(testBoard.getSecondBrewAroma()).isEqualTo(DEFAULT_SECOND_BREW_AROMA);
        assertThat(testBoard.getSecondBrewMood()).isEqualTo(DEFAULT_SECOND_BREW_MOOD);
        assertThat(testBoard.getThirdBrewPic()).isEqualTo(DEFAULT_THIRD_BREW_PIC);
        assertThat(testBoard.getThirdBrewSoup()).isEqualTo(DEFAULT_THIRD_BREW_SOUP);
        assertThat(testBoard.getThirdBrewAroma()).isEqualTo(DEFAULT_THIRD_BREW_AROMA);
        assertThat(testBoard.getThirdBrewMood()).isEqualTo(DEFAULT_THIRD_BREW_MOOD);
        assertThat(testBoard.getBrewTimes()).isEqualTo(DEFAULT_BREW_TIMES);
        assertThat(testBoard.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the Board in ElasticSearch
        Board boardEs = boardSearchRepository.findOne(testBoard.getId());
        assertThat(boardEs).isEqualToComparingFieldByField(testBoard);
    }

    @Test
    @Transactional
    public void createBoardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boardRepository.findAll().size();

        // Create the Board with an existing ID
        Board existingBoard = new Board();
        existingBoard.setId(1L);
        BoardDTO existingBoardDTO = boardMapper.boardToBoardDTO(existingBoard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoardMockMvc.perform(post("/api/boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBoardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBoards() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        // Get all the boardList
        restBoardMockMvc.perform(get("/api/boards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(board.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].productionDate").value(hasItem(sameInstant(DEFAULT_PRODUCTION_DATE))))
            .andExpect(jsonPath("$.[*].purchaseTime").value(hasItem(sameInstant(DEFAULT_PURCHASE_TIME))))
            .andExpect(jsonPath("$.[*].productionPlace").value(hasItem(DEFAULT_PRODUCTION_PLACE.toString())))
            .andExpect(jsonPath("$.[*].purchasePlace").value(hasItem(DEFAULT_PURCHASE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].process").value(hasItem(DEFAULT_PROCESS.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].surrounding").value(hasItem(DEFAULT_SURROUNDING.toString())))
            .andExpect(jsonPath("$.[*].teaPerson").value(hasItem(DEFAULT_TEA_PERSON.toString())))
            .andExpect(jsonPath("$.[*].drinkingPerson").value(hasItem(DEFAULT_DRINKING_PERSON.toString())))
            .andExpect(jsonPath("$.[*].mood").value(hasItem(DEFAULT_MOOD.toString())))
            .andExpect(jsonPath("$.[*].teaSet").value(hasItem(DEFAULT_TEA_SET.toString())))
            .andExpect(jsonPath("$.[*].teaPic").value(hasItem(DEFAULT_TEA_PIC.toString())))
            .andExpect(jsonPath("$.[*].packagePic").value(hasItem(DEFAULT_PACKAGE_PIC.toString())))
            .andExpect(jsonPath("$.[*].storageMethod").value(hasItem(DEFAULT_STORAGE_METHOD.toString())))
            .andExpect(jsonPath("$.[*].shape").value(hasItem(DEFAULT_SHAPE.toString())))
            .andExpect(jsonPath("$.[*].aroma").value(hasItem(DEFAULT_AROMA.toString())))
            .andExpect(jsonPath("$.[*].hotAroma").value(hasItem(DEFAULT_HOT_AROMA.toString())))
            .andExpect(jsonPath("$.[*].teaWash").value(hasItem(DEFAULT_TEA_WASH.toString())))
            .andExpect(jsonPath("$.[*].boilingAroma").value(hasItem(DEFAULT_BOILING_AROMA.toString())))
            .andExpect(jsonPath("$.[*].cupAroma").value(hasItem(DEFAULT_CUP_AROMA.toString())))
            .andExpect(jsonPath("$.[*].firstBrewPic").value(hasItem(DEFAULT_FIRST_BREW_PIC.toString())))
            .andExpect(jsonPath("$.[*].firstBrewingSoup").value(hasItem(DEFAULT_FIRST_BREWING_SOUP.toString())))
            .andExpect(jsonPath("$.[*].firstBrewAroma").value(hasItem(DEFAULT_FIRST_BREW_AROMA.toString())))
            .andExpect(jsonPath("$.[*].firstBrewMood").value(hasItem(DEFAULT_FIRST_BREW_MOOD.toString())))
            .andExpect(jsonPath("$.[*].secondBrewPic").value(hasItem(DEFAULT_SECOND_BREW_PIC.toString())))
            .andExpect(jsonPath("$.[*].secondBrewSoup").value(hasItem(DEFAULT_SECOND_BREW_SOUP.toString())))
            .andExpect(jsonPath("$.[*].secondBrewAroma").value(hasItem(DEFAULT_SECOND_BREW_AROMA.toString())))
            .andExpect(jsonPath("$.[*].secondBrewMood").value(hasItem(DEFAULT_SECOND_BREW_MOOD.toString())))
            .andExpect(jsonPath("$.[*].thirdBrewPic").value(hasItem(DEFAULT_THIRD_BREW_PIC.toString())))
            .andExpect(jsonPath("$.[*].thirdBrewSoup").value(hasItem(DEFAULT_THIRD_BREW_SOUP.toString())))
            .andExpect(jsonPath("$.[*].thirdBrewAroma").value(hasItem(DEFAULT_THIRD_BREW_AROMA.toString())))
            .andExpect(jsonPath("$.[*].thirdBrewMood").value(hasItem(DEFAULT_THIRD_BREW_MOOD.toString())))
            .andExpect(jsonPath("$.[*].brewTimes").value(hasItem(DEFAULT_BREW_TIMES.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        // Get the board
        restBoardMockMvc.perform(get("/api/boards/{id}", board.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(board.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.productionDate").value(sameInstant(DEFAULT_PRODUCTION_DATE)))
            .andExpect(jsonPath("$.purchaseTime").value(sameInstant(DEFAULT_PURCHASE_TIME)))
            .andExpect(jsonPath("$.productionPlace").value(DEFAULT_PRODUCTION_PLACE.toString()))
            .andExpect(jsonPath("$.purchasePlace").value(DEFAULT_PURCHASE_PLACE.toString()))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER.toString()))
            .andExpect(jsonPath("$.process").value(DEFAULT_PROCESS.toString()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.surrounding").value(DEFAULT_SURROUNDING.toString()))
            .andExpect(jsonPath("$.teaPerson").value(DEFAULT_TEA_PERSON.toString()))
            .andExpect(jsonPath("$.drinkingPerson").value(DEFAULT_DRINKING_PERSON.toString()))
            .andExpect(jsonPath("$.mood").value(DEFAULT_MOOD.toString()))
            .andExpect(jsonPath("$.teaSet").value(DEFAULT_TEA_SET.toString()))
            .andExpect(jsonPath("$.teaPic").value(DEFAULT_TEA_PIC.toString()))
            .andExpect(jsonPath("$.packagePic").value(DEFAULT_PACKAGE_PIC.toString()))
            .andExpect(jsonPath("$.storageMethod").value(DEFAULT_STORAGE_METHOD.toString()))
            .andExpect(jsonPath("$.shape").value(DEFAULT_SHAPE.toString()))
            .andExpect(jsonPath("$.aroma").value(DEFAULT_AROMA.toString()))
            .andExpect(jsonPath("$.hotAroma").value(DEFAULT_HOT_AROMA.toString()))
            .andExpect(jsonPath("$.teaWash").value(DEFAULT_TEA_WASH.toString()))
            .andExpect(jsonPath("$.boilingAroma").value(DEFAULT_BOILING_AROMA.toString()))
            .andExpect(jsonPath("$.cupAroma").value(DEFAULT_CUP_AROMA.toString()))
            .andExpect(jsonPath("$.firstBrewPic").value(DEFAULT_FIRST_BREW_PIC.toString()))
            .andExpect(jsonPath("$.firstBrewingSoup").value(DEFAULT_FIRST_BREWING_SOUP.toString()))
            .andExpect(jsonPath("$.firstBrewAroma").value(DEFAULT_FIRST_BREW_AROMA.toString()))
            .andExpect(jsonPath("$.firstBrewMood").value(DEFAULT_FIRST_BREW_MOOD.toString()))
            .andExpect(jsonPath("$.secondBrewPic").value(DEFAULT_SECOND_BREW_PIC.toString()))
            .andExpect(jsonPath("$.secondBrewSoup").value(DEFAULT_SECOND_BREW_SOUP.toString()))
            .andExpect(jsonPath("$.secondBrewAroma").value(DEFAULT_SECOND_BREW_AROMA.toString()))
            .andExpect(jsonPath("$.secondBrewMood").value(DEFAULT_SECOND_BREW_MOOD.toString()))
            .andExpect(jsonPath("$.thirdBrewPic").value(DEFAULT_THIRD_BREW_PIC.toString()))
            .andExpect(jsonPath("$.thirdBrewSoup").value(DEFAULT_THIRD_BREW_SOUP.toString()))
            .andExpect(jsonPath("$.thirdBrewAroma").value(DEFAULT_THIRD_BREW_AROMA.toString()))
            .andExpect(jsonPath("$.thirdBrewMood").value(DEFAULT_THIRD_BREW_MOOD.toString()))
            .andExpect(jsonPath("$.brewTimes").value(DEFAULT_BREW_TIMES.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoard() throws Exception {
        // Get the board
        restBoardMockMvc.perform(get("/api/boards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);
        boardSearchRepository.save(board);
        int databaseSizeBeforeUpdate = boardRepository.findAll().size();

        // Update the board
        Board updatedBoard = boardRepository.findOne(board.getId());
        updatedBoard
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE)
                .productionDate(UPDATED_PRODUCTION_DATE)
                .purchaseTime(UPDATED_PURCHASE_TIME)
                .productionPlace(UPDATED_PRODUCTION_PLACE)
                .purchasePlace(UPDATED_PURCHASE_PLACE)
                .provider(UPDATED_PROVIDER)
                .process(UPDATED_PROCESS)
                .time(UPDATED_TIME)
                .place(UPDATED_PLACE)
                .surrounding(UPDATED_SURROUNDING)
                .teaPerson(UPDATED_TEA_PERSON)
                .drinkingPerson(UPDATED_DRINKING_PERSON)
                .mood(UPDATED_MOOD)
                .teaSet(UPDATED_TEA_SET)
                .teaPic(UPDATED_TEA_PIC)
                .packagePic(UPDATED_PACKAGE_PIC)
                .storageMethod(UPDATED_STORAGE_METHOD)
                .shape(UPDATED_SHAPE)
                .aroma(UPDATED_AROMA)
                .hotAroma(UPDATED_HOT_AROMA)
                .teaWash(UPDATED_TEA_WASH)
                .boilingAroma(UPDATED_BOILING_AROMA)
                .cupAroma(UPDATED_CUP_AROMA)
                .firstBrewPic(UPDATED_FIRST_BREW_PIC)
                .firstBrewingSoup(UPDATED_FIRST_BREWING_SOUP)
                .firstBrewAroma(UPDATED_FIRST_BREW_AROMA)
                .firstBrewMood(UPDATED_FIRST_BREW_MOOD)
                .secondBrewPic(UPDATED_SECOND_BREW_PIC)
                .secondBrewSoup(UPDATED_SECOND_BREW_SOUP)
                .secondBrewAroma(UPDATED_SECOND_BREW_AROMA)
                .secondBrewMood(UPDATED_SECOND_BREW_MOOD)
                .thirdBrewPic(UPDATED_THIRD_BREW_PIC)
                .thirdBrewSoup(UPDATED_THIRD_BREW_SOUP)
                .thirdBrewAroma(UPDATED_THIRD_BREW_AROMA)
                .thirdBrewMood(UPDATED_THIRD_BREW_MOOD)
                .brewTimes(UPDATED_BREW_TIMES)
                .comment(UPDATED_COMMENT);
        BoardDTO boardDTO = boardMapper.boardToBoardDTO(updatedBoard);

        restBoardMockMvc.perform(put("/api/boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardDTO)))
            .andExpect(status().isOk());

        // Validate the Board in the database
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).hasSize(databaseSizeBeforeUpdate);
        Board testBoard = boardList.get(boardList.size() - 1);
        assertThat(testBoard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBoard.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBoard.getProductionDate()).isEqualTo(UPDATED_PRODUCTION_DATE);
        assertThat(testBoard.getPurchaseTime()).isEqualTo(UPDATED_PURCHASE_TIME);
        assertThat(testBoard.getProductionPlace()).isEqualTo(UPDATED_PRODUCTION_PLACE);
        assertThat(testBoard.getPurchasePlace()).isEqualTo(UPDATED_PURCHASE_PLACE);
        assertThat(testBoard.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testBoard.getProcess()).isEqualTo(UPDATED_PROCESS);
        assertThat(testBoard.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testBoard.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testBoard.getSurrounding()).isEqualTo(UPDATED_SURROUNDING);
        assertThat(testBoard.getTeaPerson()).isEqualTo(UPDATED_TEA_PERSON);
        assertThat(testBoard.getDrinkingPerson()).isEqualTo(UPDATED_DRINKING_PERSON);
        assertThat(testBoard.getMood()).isEqualTo(UPDATED_MOOD);
        assertThat(testBoard.getTeaSet()).isEqualTo(UPDATED_TEA_SET);
        assertThat(testBoard.getTeaPic()).isEqualTo(UPDATED_TEA_PIC);
        assertThat(testBoard.getPackagePic()).isEqualTo(UPDATED_PACKAGE_PIC);
        assertThat(testBoard.getStorageMethod()).isEqualTo(UPDATED_STORAGE_METHOD);
        assertThat(testBoard.getShape()).isEqualTo(UPDATED_SHAPE);
        assertThat(testBoard.getAroma()).isEqualTo(UPDATED_AROMA);
        assertThat(testBoard.getHotAroma()).isEqualTo(UPDATED_HOT_AROMA);
        assertThat(testBoard.getTeaWash()).isEqualTo(UPDATED_TEA_WASH);
        assertThat(testBoard.getBoilingAroma()).isEqualTo(UPDATED_BOILING_AROMA);
        assertThat(testBoard.getCupAroma()).isEqualTo(UPDATED_CUP_AROMA);
        assertThat(testBoard.getFirstBrewPic()).isEqualTo(UPDATED_FIRST_BREW_PIC);
        assertThat(testBoard.getFirstBrewingSoup()).isEqualTo(UPDATED_FIRST_BREWING_SOUP);
        assertThat(testBoard.getFirstBrewAroma()).isEqualTo(UPDATED_FIRST_BREW_AROMA);
        assertThat(testBoard.getFirstBrewMood()).isEqualTo(UPDATED_FIRST_BREW_MOOD);
        assertThat(testBoard.getSecondBrewPic()).isEqualTo(UPDATED_SECOND_BREW_PIC);
        assertThat(testBoard.getSecondBrewSoup()).isEqualTo(UPDATED_SECOND_BREW_SOUP);
        assertThat(testBoard.getSecondBrewAroma()).isEqualTo(UPDATED_SECOND_BREW_AROMA);
        assertThat(testBoard.getSecondBrewMood()).isEqualTo(UPDATED_SECOND_BREW_MOOD);
        assertThat(testBoard.getThirdBrewPic()).isEqualTo(UPDATED_THIRD_BREW_PIC);
        assertThat(testBoard.getThirdBrewSoup()).isEqualTo(UPDATED_THIRD_BREW_SOUP);
        assertThat(testBoard.getThirdBrewAroma()).isEqualTo(UPDATED_THIRD_BREW_AROMA);
        assertThat(testBoard.getThirdBrewMood()).isEqualTo(UPDATED_THIRD_BREW_MOOD);
        assertThat(testBoard.getBrewTimes()).isEqualTo(UPDATED_BREW_TIMES);
        assertThat(testBoard.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the Board in ElasticSearch
        Board boardEs = boardSearchRepository.findOne(testBoard.getId());
        assertThat(boardEs).isEqualToComparingFieldByField(testBoard);
    }

    @Test
    @Transactional
    public void updateNonExistingBoard() throws Exception {
        int databaseSizeBeforeUpdate = boardRepository.findAll().size();

        // Create the Board
        BoardDTO boardDTO = boardMapper.boardToBoardDTO(board);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBoardMockMvc.perform(put("/api/boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardDTO)))
            .andExpect(status().isCreated());

        // Validate the Board in the database
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);
        boardSearchRepository.save(board);
        int databaseSizeBeforeDelete = boardRepository.findAll().size();

        // Get the board
        restBoardMockMvc.perform(delete("/api/boards/{id}", board.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean boardExistsInEs = boardSearchRepository.exists(board.getId());
        assertThat(boardExistsInEs).isFalse();

        // Validate the database is empty
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);
        boardSearchRepository.save(board);

        // Search the board
        restBoardMockMvc.perform(get("/api/_search/boards?query=id:" + board.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(board.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].productionDate").value(hasItem(sameInstant(DEFAULT_PRODUCTION_DATE))))
            .andExpect(jsonPath("$.[*].purchaseTime").value(hasItem(sameInstant(DEFAULT_PURCHASE_TIME))))
            .andExpect(jsonPath("$.[*].productionPlace").value(hasItem(DEFAULT_PRODUCTION_PLACE.toString())))
            .andExpect(jsonPath("$.[*].purchasePlace").value(hasItem(DEFAULT_PURCHASE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].process").value(hasItem(DEFAULT_PROCESS.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].surrounding").value(hasItem(DEFAULT_SURROUNDING.toString())))
            .andExpect(jsonPath("$.[*].teaPerson").value(hasItem(DEFAULT_TEA_PERSON.toString())))
            .andExpect(jsonPath("$.[*].drinkingPerson").value(hasItem(DEFAULT_DRINKING_PERSON.toString())))
            .andExpect(jsonPath("$.[*].mood").value(hasItem(DEFAULT_MOOD.toString())))
            .andExpect(jsonPath("$.[*].teaSet").value(hasItem(DEFAULT_TEA_SET.toString())))
            .andExpect(jsonPath("$.[*].teaPic").value(hasItem(DEFAULT_TEA_PIC.toString())))
            .andExpect(jsonPath("$.[*].packagePic").value(hasItem(DEFAULT_PACKAGE_PIC.toString())))
            .andExpect(jsonPath("$.[*].storageMethod").value(hasItem(DEFAULT_STORAGE_METHOD.toString())))
            .andExpect(jsonPath("$.[*].shape").value(hasItem(DEFAULT_SHAPE.toString())))
            .andExpect(jsonPath("$.[*].aroma").value(hasItem(DEFAULT_AROMA.toString())))
            .andExpect(jsonPath("$.[*].hotAroma").value(hasItem(DEFAULT_HOT_AROMA.toString())))
            .andExpect(jsonPath("$.[*].teaWash").value(hasItem(DEFAULT_TEA_WASH.toString())))
            .andExpect(jsonPath("$.[*].boilingAroma").value(hasItem(DEFAULT_BOILING_AROMA.toString())))
            .andExpect(jsonPath("$.[*].cupAroma").value(hasItem(DEFAULT_CUP_AROMA.toString())))
            .andExpect(jsonPath("$.[*].firstBrewPic").value(hasItem(DEFAULT_FIRST_BREW_PIC.toString())))
            .andExpect(jsonPath("$.[*].firstBrewingSoup").value(hasItem(DEFAULT_FIRST_BREWING_SOUP.toString())))
            .andExpect(jsonPath("$.[*].firstBrewAroma").value(hasItem(DEFAULT_FIRST_BREW_AROMA.toString())))
            .andExpect(jsonPath("$.[*].firstBrewMood").value(hasItem(DEFAULT_FIRST_BREW_MOOD.toString())))
            .andExpect(jsonPath("$.[*].secondBrewPic").value(hasItem(DEFAULT_SECOND_BREW_PIC.toString())))
            .andExpect(jsonPath("$.[*].secondBrewSoup").value(hasItem(DEFAULT_SECOND_BREW_SOUP.toString())))
            .andExpect(jsonPath("$.[*].secondBrewAroma").value(hasItem(DEFAULT_SECOND_BREW_AROMA.toString())))
            .andExpect(jsonPath("$.[*].secondBrewMood").value(hasItem(DEFAULT_SECOND_BREW_MOOD.toString())))
            .andExpect(jsonPath("$.[*].thirdBrewPic").value(hasItem(DEFAULT_THIRD_BREW_PIC.toString())))
            .andExpect(jsonPath("$.[*].thirdBrewSoup").value(hasItem(DEFAULT_THIRD_BREW_SOUP.toString())))
            .andExpect(jsonPath("$.[*].thirdBrewAroma").value(hasItem(DEFAULT_THIRD_BREW_AROMA.toString())))
            .andExpect(jsonPath("$.[*].thirdBrewMood").value(hasItem(DEFAULT_THIRD_BREW_MOOD.toString())))
            .andExpect(jsonPath("$.[*].brewTimes").value(hasItem(DEFAULT_BREW_TIMES.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
}
