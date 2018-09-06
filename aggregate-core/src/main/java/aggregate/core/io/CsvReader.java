package aggregate.core.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.Constant;
import aggregate.core.model.ColumnSet;

/**
 * Class for reading csv files.
 */
@Component
public class CsvReader {

  /**
   * Read by argument header and file path to List of {@link ColumnSet}.
   *
   * @param headers Array of csv file headers
   * @param filePath Absolute csv file path
   * @return List of {@link ColumnSet}
   */
  public List<ColumnSet> read(BaseSheetHeader[] headers, String filePath) {
    return readMain(headers, filePath, readLineFunction());
  }

  /**
   * Read with quote by argument header and file path to List of {@link ColumnSet}.
   *
   * @param headers Array of csv file headers
   * @param filePath Absolute csv file path
   * @param quote Quote of csv file
   * @return List of {@link ColumnSet}
   */
  public List<ColumnSet> read(BaseSheetHeader[] headers, String filePath, String quote) {
    return readMain(headers, filePath, readLineFunction(quote));
  }

  private List<ColumnSet> readMain(BaseSheetHeader[] headers, String filePath,
      Function<String, String[]> readLineFunction) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line = br.readLine();
      // read header
      List<BaseSheetHeader> fileHeaders = readHeader(headers, readLineFunction.apply(line));
      // read body
      List<ColumnSet> result = new ArrayList<>();
      int row = 1;
      while ((line = br.readLine()) != null) {
        String[] columns = readLineFunction.apply(line);
        validateLine(fileHeaders, columns, row++);
        result.add(new ColumnSet(fileHeaders, columns));
      }
      return result;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<BaseSheetHeader> readHeader(BaseSheetHeader[] headers, String[] values) {
    return Arrays.asList(values).stream().map(value -> getHeaderByHeaderName(headers, value))
        .collect(Collectors.toList());
  }

  private BaseSheetHeader getHeaderByHeaderName(BaseSheetHeader[] headers, String headerName) {
    Optional<BaseSheetHeader> result = Arrays.stream(headers)
        .filter(header -> header.getHeaderName().equals(headerName)).findFirst();
    if (result.isPresent()) {
      return result.get();
    } else {
      String headerEnum = headers.getClass().getSimpleName().replaceAll("\\[\\]", "");
      throw new RuntimeException(
          String.format("Header is not found in %s: %s", headerEnum, headerName));
    }
  }

  private void validateLine(List<BaseSheetHeader> headers, String[] columns, int row) {
    // validate headers and columns size
    if (headers.size() != columns.length) {
      throw new RuntimeException("Missmatch column size between header and line " + row);
    }
  }

  private Function<String, String[]> readLineFunction() {
    return line -> line.split(Constant.COMMA);
  }

  private Function<String, String[]> readLineFunction(String quote) {
    return line -> getLineArrayWithQuote(line, quote);
  }

  private String[] getLineArrayWithQuote(String line, String quote) {
    Matcher matcher = Pattern.compile(String.format("%s.*?%s", quote, quote)).matcher(line);
    List<String> result = new ArrayList<>();
    while (matcher.find()) {
      result.add(matcher.group().replaceAll(quote, ""));
    }
    return result.toArray(new String[result.size()]);
  }

}
